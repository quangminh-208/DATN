package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Order;
import io.github.pudo58.base.service.OrderService;
import io.github.pudo58.dto.OrderRejectRequest;
import io.github.pudo58.dto.OrderRequest;
import io.github.pudo58.record.AlertResponseRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController extends AbstractController<Order> {
    private final OrderService orderService;

    @PostMapping("/findBySearch")
    public Page<Order> findBySearch(@RequestBody OrderRequest model) {
        return orderService.findBySearch(model);
    }

    @PostMapping("/approve/{id}")
    public AlertResponseRecord approve(@PathVariable UUID id) {
        return orderService.approve(id);
    }

    @PostMapping("/reject")
    public AlertResponseRecord reject(@RequestBody OrderRejectRequest model) {
        return orderService.reject(model);
    }

    @GetMapping("/getDetail/{id}")
    public Order get(@PathVariable UUID id) {
        return orderService.findById(id);
    }

    @GetMapping("/chart")
    public int[] getChart() {
        return orderService.getChart();
    }
}
