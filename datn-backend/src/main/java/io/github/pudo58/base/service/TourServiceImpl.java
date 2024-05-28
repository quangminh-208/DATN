package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.*;
import io.github.pudo58.base.repo.OrderRepo;
import io.github.pudo58.base.repo.TourRepo;
import io.github.pudo58.base.repo.VoucherRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.constant.OrderConst;
import io.github.pudo58.constant.TourConst;
import io.github.pudo58.constant.VoucherConst;
import io.github.pudo58.dto.TourRequest;
import io.github.pudo58.dto.TourSearchRequest;
import io.github.pudo58.record.AlertResponseRecord;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TourServiceImpl extends AbstractService<Tour> implements TourService {
    private final TourRepo tourRepo;
    private final OrderRepo orderRepo;
    private final VoucherRepo voucherRepo;

    @Value("${vietqr.url}")
    private String vietQRURL;

    @Override
    public Tour findById(UUID uuid) {
        Tour tour = super.findById(uuid);
        if (tour != null) {
            tour.setCategoryRequest(tour.getCategory());
        }
        return tour;
    }

    @Override
    public Page<Tour> findBySearch(TourSearchRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<Tour> tours = tourRepo.findBySearch(model, pageable);
        List<Tour> tourList = tours.getContent();
        for (Tour tour : tourList) {
            tour.setCategoryRequest(tour.getCategory());
        }
        return new PageImpl<>(tourList, pageable, tours.getTotalElements());
    }

    @Override
    public List<Tour> findByNewest(TourSearchRequest model) {
        return tourRepo.findByNewest(model);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> bookTour(TourRequest model) {
        Tour tour = tourRepo.findById(model.getTourId()).orElse(null);
        if (tour == null) {
            return ResponseEntity.notFound().build();
        }
        Assert.notNull(User.getContext(), "Bạn cần đăng nhập để đặt tour");
        Assert.notNull(model.getQuantityForAdult(), "Số vé người lớn không được để trống");
        if (model.getQuantityForChildren() == null) {
            model.setQuantityForChildren(0);
        }
        if (model.getQuantityForBaby() == null) {
            model.setQuantityForBaby(0);
        }
        boolean existOrder = orderRepo.getByTourIdAndUserIdAndStatusIn(model.getTourId(), User.getContext().getId(), Collections.singletonList(OrderConst.STATUS_PENDING)) != null;
        Assert.isTrue(!existOrder, "Bạn đã đặt tour này rồi, vui lòng thanh toán trước khi đặt tiếp");
        Assert.isTrue(model.getQuantityForAdult() > 0, "Số vé người lớn phải lớn hơn 0");
        Assert.isTrue(model.getQuantityForChildren() >= 0, "Số vé trẻ em phải lớn hơn hoặc bằng 0");
        Assert.isTrue(model.getQuantityForBaby() >= 0, "Số vé em bé phải lớn hơn hoặc bằng 0");
        if (tour.getSoldForAdult() + model.getQuantityForAdult() > tour.getQuantityForAdult()) {
            throw new IllegalArgumentException("Số vé người lớn không đủ");
        }
        if (tour.getSoldForChild() + model.getQuantityForChildren() > tour.getQuantityForChild()) {
            throw new IllegalArgumentException("Số vé trẻ em không đủ");
        }
        if (tour.getSoldForBaby() + model.getQuantityForBaby() > tour.getQuantityForBaby()) {
            throw new IllegalArgumentException("Số vé em bé không đủ");
        }
        int total = model.getQuantityForAdult() * tour.getPriceAdult() +
                model.getQuantityForChildren() * tour.getPriceChild() +
                model.getQuantityForBaby() * tour.getPriceBaby();

        tour.setSoldForAdult(tour.getSoldForAdult() + model.getQuantityForAdult());
        tour.setSoldForChild(tour.getSoldForChild() + model.getQuantityForChildren());
        tour.setSoldForBaby(tour.getSoldForBaby() + model.getQuantityForBaby());
        tourRepo.save(tour);
        Order order = new Order();
        if (model.getVoucherCode() != null && !model.getVoucherCode().isBlank()) {
            Voucher voucher = voucherRepo.findByCode(model.getVoucherCode());
            Assert.notNull(voucher, "Mã giảm giá không hợp lệ");
            Assert.isTrue(voucher.getQuantity() > 0, "Mã giảm giá đã hết lượt sử dụng");
            Assert.isTrue(voucher.getStartDate().before(new Date()), "Mã giảm giá đã hết hạn");
            Assert.isTrue(voucher.getEndDate().after(new Date()), "Mã giảm giá đã hết hạn");
            // calc discount
            if (voucher.getDiscountType().equals(VoucherConst.DISCOUNT_TYPE_MONEY)) {
                total -= voucher.getDiscountValue();
            } else if (voucher.getDiscountType().equals(VoucherConst.DISCOUNT_TYPE_PERCENTAGE)) {
                total -= total * voucher.getDiscountValue() / 100;
            }
            Assert.isTrue(total >= 0, "Mã giảm giá không hợp lệ");
            voucher.setQuantity(voucher.getQuantity() - 1);
            voucherRepo.save(voucher);
        }
        order.setTour(tour);
        order.setUser(User.getContext());
        order.setQuantityForAdult(model.getQuantityForAdult());
        order.setQuantityForChildren(model.getQuantityForChildren());
        order.setQuantityForBaby(model.getQuantityForBaby());
        order.setTotalAdult(model.getQuantityForAdult() * tour.getPriceAdult());
        order.setTotalChildren(model.getQuantityForChildren() * tour.getPriceChild());
        order.setTotalBaby(model.getQuantityForBaby() * tour.getPriceBaby());
        order.setTotalPayment(total);
        order.setStatus(OrderConst.STATUS_PENDING);
        if (model.getCustomers() != null || !model.getCustomers().isEmpty()) {
            order.setCustomers(model.getCustomers());
        }
        if (model.getVoucherCode() != null && !model.getVoucherCode().isBlank()) {
            order.setVoucher(voucherRepo.findByCode(model.getVoucherCode()));
        }
        orderRepo.save(order);
        return ResponseEntity.ok(new AlertResponseRecord("Đặt tour thành công, bạn vui lòng vào mục lịch sử đặt tour để thanh toán", HttpStatus.OK.value()));
    }

    @Override
    public Tour save(Tour tour) {
        if (tour.getCategoryRequest() != null) {
            tour.setCategory(tour.getCategoryRequest());
        }
        this.addDetail(tour);
        return super.save(tour);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Tour update(UUID uuid, Tour tour) {
        Tour oldTour = tourRepo.findById(uuid).orElse(null);
        Assert.notNull(oldTour, "Tour not found");
        oldTour.setName(tour.getName());
        oldTour.setDescription(tour.getDescription());
        oldTour.setLocation(tour.getLocation());
        oldTour.setDeparture(tour.getDeparture());
        oldTour.setStartDate(tour.getStartDate());
        oldTour.setDuration(tour.getDuration());
        oldTour.setPolicy(tour.getPolicy());
        oldTour.setVehicle(tour.getVehicle());
        oldTour.setQuantityForAdult(tour.getQuantityForAdult());
        oldTour.setQuantityForChild(tour.getQuantityForChild());
        oldTour.setQuantityForBaby(tour.getQuantityForBaby());
        oldTour.setPriceAdult(tour.getPriceAdult());
        oldTour.setPriceChild(tour.getPriceChild());
        oldTour.setPriceBaby(tour.getPriceBaby());
        if (tour.getImageData() != null) {
            tour.setImageData(tour.getImageData().substring(tour.getImageData().indexOf(",") + 1));
            oldTour.setImage(Base64.getDecoder().decode(tour.getImageData()));
        }
        this.addDetail(tour);
        oldTour.setAttraction(tour.getAttraction());
        oldTour.setCategory(tour.getCategoryRequest());
        oldTour.setTourGalleryList(tour.getTourGalleryList());
        oldTour.setTourScheduleList(tour.getTourScheduleList());
        return tourRepo.save(oldTour);
    }

    @Override
    public ResponseEntity<?> getQRCodePurchase(UUID id) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException, InterruptedException {
        Assert.notNull(User.getContext(), "Bạn cần đăng nhập để thanh toán");
        Order order = orderRepo.getByTourIdAndUserIdAndStatusIn(id, User.getContext().getId(), List.of(OrderConst.STATUS_PENDING, OrderConst.STATUS_CONFIRMED));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        final String jsonPayload = "{\n" +
                "  \"accountNo\": \"0704145768\",\n" +
                "  \"accountName\": \"LA VAN THO\",\n" +
                "  \"acqId\": 970432,\n" +
                "  \"amount\": " + order.getTotalPayment() + ",\n" +
                "  \"addInfo\": \"" + User.getContext().getUsername() + "- Thanh toan tour gia tri " + order.getTotalPayment() + " VND\",\n" +
                "  \"format\": \"text\",\n" +
                "  \"template\": \"compact2\"\n" +
                "}";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(vietQRURL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
        // get data from vietqr
        HttpClient client = HttpClient.newBuilder()
                .sslContext(SSLContext.getDefault())
                .build();
        HttpResponse<?> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> data = new HashMap<>();
        data.put("qrCode", response.body());
        int currentStep = 0;
        if (OrderConst.STATUS_PENDING == order.getStatus()) {
            currentStep = 2;
        } else if (OrderConst.STATUS_CONFIRMED == order.getStatus()) {
            currentStep = 3;
        } else if (OrderConst.STATUS_COMPLETED == order.getStatus()) {
            currentStep = 4;
        }
        data.put("currentStep", currentStep);
        return ResponseEntity.ok(data);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public void deleteById(UUID uuid) {
        Tour tour = tourRepo.findById(uuid).orElse(null);
        Assert.notNull(tour, "Tour not found");
        tour.setStatus(TourConst.STATUS_INACTIVE);
        tourRepo.save(tour);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ResponseEntity<?> successPurchase(UUID id) {
        Order order = orderRepo.getByTourIdAndUserIdAndStatusIn(id, User.getContext().getId(), Collections.singletonList(OrderConst.STATUS_PENDING));
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        order.setStatus(OrderConst.STATUS_CONFIRMED);
        orderRepo.save(order);
        return ResponseEntity.ok(new AlertResponseRecord("Bạn đã xác nhận thanh toán, chúng tôi sẽ duyệt trong 1 ngày làm việc", HttpStatus.OK.value()));
    }

    private void addDetail(Tour tour) {
        if (tour.getTourGalleryList() != null && !tour.getTourGalleryList().isEmpty()) {
            for (TourGallery tourGallery : tour.getTourGalleryList()) {
                String imageData = tourGallery.getImageData();
                if (imageData != null) {
                    tourGallery.setImageData(imageData.substring(imageData.indexOf(",") + 1));
                    tourGallery.setImage(Base64.getDecoder().decode(tourGallery.getImageData()));
                }
            }
        }
        if (tour.getTourScheduleList() != null && !tour.getTourScheduleList().isEmpty()) {
            for (TourSchedule tourSchedule : tour.getTourScheduleList()) {
                String imageData = tourSchedule.getImageData();
                if (imageData != null) {
                    tourSchedule.setImageData(imageData.substring(imageData.indexOf(",") + 1));
                    tourSchedule.setImage(Base64.getDecoder().decode(tourSchedule.getImageData()));
                }
            }
        }
    }
}
