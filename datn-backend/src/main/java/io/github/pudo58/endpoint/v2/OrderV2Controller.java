package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Order;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.service.OrderService;
import io.github.pudo58.dto.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/v2/order")
@RequiredArgsConstructor
public class OrderV2Controller {
    private final OrderService orderService;

    @PostMapping("/findBySearchForUser")
    public Page<Order> findBySearchForUser(@RequestBody OrderRequest model) {
        model.setUserId(User.getContextId());
        return this.orderService.findBySearchForUser(model);
    }

    @GetMapping("/get/{id}")
    public Order get(@PathVariable UUID id) {
        return this.orderService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        this.orderService.deleteById(id);
    }
}
