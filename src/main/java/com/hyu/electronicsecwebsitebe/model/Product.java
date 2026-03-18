package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "sanpham")
public class Product {
    @Id
    @Column(name = "sp_id")
    private String id;

    @Column(name = "sp_name")
    private String name;

    @Column(name = "sp_price")
    private BigDecimal price;

    @Column(name = "sp_description")
    private String description;

    @Column(name = "sp_image")
    private String image;

    @Column(name = "sp_stock")
    private int stock;

    @ManyToOne
    @JoinColumn(name = "sp_category_id")
    private ProductCategory category;

    @ManyToOne
    @JoinColumn(name = "sp_brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "sp_promotion_id")
    private Promotion promotion;

    public String getImage() {
        if (image == null || image.isBlank()) return null;
        // Lấy folder theo category
        String categoryFolder="";
        if (category != null && category.getId() != null) {
            categoryFolder = category.getId();
        }
        return "/photos/products/" + categoryFolder + "/" + image;
    }

    @Transient
    private BigDecimal discountedPrice;

    public BigDecimal getDiscountedPrice() {
        if (promotion == null || !promotion.isActive()) {
            return price;
        }

        BigDecimal percent = BigDecimal.valueOf(promotion.getDiscountPercentage())
                .divide(BigDecimal.valueOf(100));

        return price.subtract(price.multiply(percent));
    }
}
