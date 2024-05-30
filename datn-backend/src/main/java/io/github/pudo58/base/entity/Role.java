package io.github.pudo58.base.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
public class Role extends BaseEntity{
    private String name;

    @Override
    public void preUpdate() {
        super.nothingToDo();
    }

    @Override
    public void prePersist() {
        super.nothingToDo();
    }
}
