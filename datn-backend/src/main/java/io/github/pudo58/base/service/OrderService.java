package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Order;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.OrderRejectRequest;
import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface OrderService extends Service<Order> {
    Page<Order> findBySearchForUser(OrderRequest model);

    Order get(UUID id) throws IllegalAccessException;

    Page<Order> findBySearch(OrderRequest model);

    AlertResponseRecord approve(UUID id);

    AlertResponseRecord reject(OrderRejectRequest model);

    int[] getChart();
}
