package com.sonht.e_commerce_webapp_spring_boot.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "status")
    private String status;

    @Column(name = "versionName")
    private String versionName;

    @Column(name = "created_at", columnDefinition = "datetime(6)")
    private Date createdAt;

    @Column(name = "updated_at", columnDefinition = "datetime(6)")
    private Date updatedAt;

    @Column(name = "isDelete")
    private Boolean isDelete; // Sử dụng Boolean để ánh xạ kiểu bit

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> productImages;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id") // FK tới Brand
    private Brand brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductSize> sizes = new ArrayList<>();

    public String primaryImage() {
        if (productImages != null && !productImages.isEmpty()) {
            for (ProductImage image : productImages) {
                if (image.isPrimary()) {
                    return image.getImageUrl();
                }
            }
        }
        return null; // Hoặc có thể trả về một URL mặc định nếu không có ảnh chính
    }

}
