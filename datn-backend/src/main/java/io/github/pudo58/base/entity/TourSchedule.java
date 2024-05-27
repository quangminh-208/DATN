package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tour_schedule")
public class TourSchedule extends BaseEntity {
    private String name;
    private String description;

    @ManyToOne(targetEntity = Tour.class, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    private Tour tour;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @Transient
    private String imageData;

    @Override
    public void preUpdate() {
        this.nothingToDo();
    }

    @Override
    public void prePersist() {
        this.nothingToDo();
    }
}
