package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Voucher;
import io.github.pudo58.base.repo.VoucherRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.dto.VoucherRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl extends AbstractService<Voucher> implements VoucherService {
    private final VoucherRepo voucherRepo;

    @Override
    public Page<Voucher> findBySearch(VoucherRequest model) {
        Pageable pageable = model.getPageable();
        return voucherRepo.findBySearch(model, pageable);
    }
}
