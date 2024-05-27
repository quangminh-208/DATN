package io.github.pudo58.dto;

import io.github.pudo58.base.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.dialect.MySQLDialect;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class TourRequest {
    private UUID tourId;
    private Integer quantityForAdult;
    private Integer quantityForChildren;
    private Integer quantityForBaby;
    private String voucherCode;
    private List<Customer> customers;
}
