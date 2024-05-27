package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Customer extends BaseEntity {
    private String name;
    private String email;
    private String address;
    private String type; // adult, children, baby

    @ManyToOne(targetEntity = Order.class)
    private Order order;

    @Override
    public void preUpdate() {

    }

    @Override
    public void prePersist() {

    }
}
