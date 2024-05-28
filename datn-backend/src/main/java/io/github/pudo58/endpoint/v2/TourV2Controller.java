package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Tour;
import io.github.pudo58.base.service.TourService;
import io.github.pudo58.dto.TourRequest;
import io.github.pudo58.dto.TourSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/tour")
public class TourV2Controller {
    private final TourService tourService;

    @GetMapping("/get/{id}")
    public Tour getTour(@PathVariable UUID id) {
        return this.tourService.findById(id);
    }

    @PostMapping("/findBySearch")
    public Page<Tour> findBySearch(@RequestBody TourSearchRequest model) {
        return this.tourService.findBySearch(model);
    }

    @PostMapping("/findByNewest")
    public List<Tour> findByNewest(@RequestBody TourSearchRequest model) {
        return this.tourService.findByNewest(model);
    }

    @GetMapping("/getQRCodePurchase/{id}")
    public ResponseEntity<?> getQRCodePurchase(@PathVariable UUID id) throws IOException, URISyntaxException, InterruptedException, NoSuchAlgorithmException, KeyManagementException {
        return this.tourService.getQRCodePurchase(id);
    }

    @PostMapping("/bookTour")
    public ResponseEntity<?> bookTour(@RequestBody TourRequest model) {
        return this.tourService.bookTour(model);
    }

    @PostMapping("/successPurchase/{id}")
    public ResponseEntity<?> successPurchase(@PathVariable UUID id) {
        return this.tourService.successPurchase(id);
    }
}
