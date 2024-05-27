package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Voucher;
import io.github.pudo58.base.service.VoucherService;
import io.github.pudo58.dto.VoucherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/voucher")
public class VoucherController extends AbstractController<Voucher> {
    private final VoucherService voucherService;

    @PostMapping("/findBySearch")
    public Page<Voucher> findBySearch(@RequestBody VoucherRequest model) {
        return voucherService.findBySearch(model);
    }
}
