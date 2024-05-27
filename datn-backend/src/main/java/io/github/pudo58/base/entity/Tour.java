package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.pudo58.constant.TourConst;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
public class Tour extends BaseEntity {
    private String name; // tên tour
    @Lob
    @Column(columnDefinition = "LONGTEXT COLLATE utf8mb4_unicode_ci")
    private String description; // mô tả tour
    private String location; // địa điểm
    private String departure; // điểm khởi hành
    private Date startDate; // ngày khởi hành
    private String duration; // thời gian
    private String policy; // chính sách
    private String vehicle; // phương tiện
    private int quantityForAdult; // số lượng người lớn
    private int quantityForChild; // số lượng trẻ em
    private int quantityForBaby; // số lượng em bé
    private int soldForAdult; // số lượng đã bán người lớn
    private int soldForChild; // số lượng đã bán trẻ em
    private int soldForBaby; // số lượng đã bán em bé
    // giá người lớn
    private int priceAdult;
    private int priceChild; // giá trẻ em
    private int priceBaby; // giá em bé

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    @Lob
    @Column(columnDefinition = "LONGTEXT COLLATE utf8mb4_unicode_ci")
    private String attraction; // điểm thu hút

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Category category;
    private String status;

    @Transient
    private String imageData;
    @Transient
    @JsonProperty("category")
    private Category categoryRequest;

    @OneToMany(targetEntity = TourGallery.class,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TourGallery> tourGalleryList;

    @OneToMany(targetEntity = TourSchedule.class ,cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<TourSchedule> tourScheduleList;

    @Override
    public void preUpdate() {

    }

    @Override
    public void prePersist() {
        this.status = TourConst.STATUS_ACTIVE;
    }
}
