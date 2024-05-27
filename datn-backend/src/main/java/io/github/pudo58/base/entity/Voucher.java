package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
public class Voucher extends BaseEntity{
    private String code;
    private Date startDate;
    private Date endDate;
    private Integer discountType;
    private Integer discountValue;
    private Integer quantity;
    @Override
    public void preUpdate() {

    }

    @Override
    public void prePersist() {

    }
}
