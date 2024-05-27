package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.github.pudo58.constant.CategoryConst;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Setter
@Getter
public class Category extends BaseEntity {
    private String name;
    private String description;
    private int status;
    @ManyToOne(targetEntity = Category.class)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Category parent;

    @Transient
    private Integer level;

    @Transient
    private UUID parentId;

    @Transient
    private List<Category> children = new ArrayList<>();

    @Transient
    private List<Tour> tours = new ArrayList<>();

    @Override
    public void preUpdate() {
        this.setCreateBy(Objects.requireNonNull(User.getContext()).getUsername());
    }

    @Override
    public void prePersist() {
        this.status = CategoryConst.STATUS_ACTIVE;
    }
}
