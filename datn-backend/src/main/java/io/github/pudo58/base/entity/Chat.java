package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class Chat extends BaseEntity {
    @ManyToOne
    User from;
    @ManyToOne
    User to;
    String message;
    long timestamp;
    @Transient
    UUID toUser;

    @Override
    public void preUpdate() {

    }

    @Override
    public void prePersist() {
        this.timestamp = System.currentTimeMillis();
    }
}
