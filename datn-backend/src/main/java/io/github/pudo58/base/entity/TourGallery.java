package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tour_gallery")
public class TourGallery extends BaseEntity{

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    private String description;
    private String title;
    private String altText;
    @ManyToOne(targetEntity = Tour.class, fetch = FetchType.LAZY)
    @JsonIgnore
    private Tour tour;

    @Transient
    private String imageData;

    @Override
    public void preUpdate() {

    }

    @Override
    public void prePersist() {

    }
}
