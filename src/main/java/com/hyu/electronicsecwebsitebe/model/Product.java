package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sanpham")
public class Product {
    @Id
    private String id;
    private String name;
    private int price;
    private String description;
    private String image;
    private int stock;

}
