package io.github.pudo58.base.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "`order`")
public class Order extends BaseEntity {
    private String code;
    @ManyToOne(targetEntity = User.class)
    private User user;
    @ManyToOne(targetEntity = Tour.class)
    private Tour tour;
    private int quantityForAdult;
    private int quantityForChildren;
    private int quantityForBaby;
    private int totalAdult;
    private int totalChildren;
    private int totalBaby;
    private int totalPayment;
    private int totalDiscount;
    private int status;
    private String reason;

    @ManyToOne(targetEntity = Voucher.class)
    private Voucher voucher;

    @OneToMany(cascade = CascadeType.ALL, targetEntity = Customer.class)
    private List<Customer> customers;

    @Override
    public void preUpdate() {
        this.nothingToDo();
    }

    @Override
    public void prePersist() {
        this.nothingToDo();
    }
}
