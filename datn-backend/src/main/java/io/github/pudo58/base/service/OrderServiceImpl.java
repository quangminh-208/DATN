package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Order;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.repo.OrderRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.constant.OrderConst;
import io.github.pudo58.dto.OrderRejectRequest;
import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends AbstractService<Order> implements OrderService {
    private final OrderRepo orderRepo;

    @Override
    public Page<Order> findBySearchForUser(OrderRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        return orderRepo.findBySearchForUser(model, pageable);
    }

    @Override
    public Order get(UUID id) throws IllegalAccessException {
        Order order = orderRepo.findById(id).orElse(null);
        User user = User.getContext();
        if (order != null && user != null && order.getUser().getId().equals(user.getId())) {
            return order;
        }
        throw new IllegalAccessException("Bạn không có quyền truy cập vào đơn hàng này");
    }

    @Override
    public Page<Order> findBySearch(OrderRequest model) {
        return orderRepo.findBySearch(model, PageRequest.of(model.getPage(), model.getSize()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AlertResponseRecord approve(UUID id) {
        Order order = orderRepo.findById(id).orElse(null);
        if (order != null && order.getStatus() == OrderConst.STATUS_CONFIRMED) {
            order.setStatus(OrderConst.STATUS_COMPLETED);
            orderRepo.save(order);
            return new AlertResponseRecord( "Duyệt đơn thành công", HttpStatus.OK.value());
        }
        throw new IllegalArgumentException("Đơn hàng không tồn tại hoặc đã được xử lý");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AlertResponseRecord reject(OrderRejectRequest model) {
        Order order = orderRepo.findById(model.getId()).orElse(null);
        if (order != null && order.getStatus() == OrderConst.STATUS_CONFIRMED) {
            order.setStatus(OrderConst.STATUS_CANCELLED);
            if (model.getReason() != null && !model.getReason().isBlank()) {
                order.setReason(model.getReason());
            }
            orderRepo.save(order);
            return new AlertResponseRecord("Từ chối đơn thành công", HttpStatus.OK.value());
        }
        throw new IllegalArgumentException("Đơn hàng không tồn tại hoặc đã được xử lý");
    }

    @Override
    public int[] getChart() {
        // đếm tour đã thanh toán, đã hủy, tổng doanhg thu
        int[] chart = new int[3];
        chart[0] = orderRepo.countByStatus(OrderConst.STATUS_COMPLETED);
        chart[1] = orderRepo.countByStatus(OrderConst.STATUS_CANCELLED);
        chart[2] = orderRepo.sumTotalPaymentByStatus(OrderConst.STATUS_COMPLETED);
        return chart;
    }
}
