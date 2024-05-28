package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Tour;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.TourRequest;
import io.github.pudo58.dto.TourSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.UUID;

public interface TourService extends Service<Tour> {
    Page<Tour> findBySearch(TourSearchRequest model);

    List<Tour> findByNewest(TourSearchRequest model);

    ResponseEntity<?> bookTour(TourRequest model);

    ResponseEntity<?> getQRCodePurchase(UUID id) throws IOException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException, InterruptedException;

    ResponseEntity<?> successPurchase(UUID id);
}
