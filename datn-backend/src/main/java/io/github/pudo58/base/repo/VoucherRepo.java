package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Voucher;
import io.github.pudo58.dto.VoucherRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher, UUID> {
    Voucher findByCode(String code);

    @Query("""
    select v from Voucher v
    where (length(:#{#model.keyword}) = 0   or v.code = :#{#model.keyword})
""")
    Page<Voucher> findBySearch(VoucherRequest model, Pageable pageable);
}
