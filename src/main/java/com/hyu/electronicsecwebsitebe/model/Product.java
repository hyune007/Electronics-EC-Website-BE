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
}
