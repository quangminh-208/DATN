package io.github.pudo58.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Post extends BaseEntity {
    private String title;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    @Override
    public void preUpdate() {
    }

    @Override
    public void prePersist() {
    }
}
