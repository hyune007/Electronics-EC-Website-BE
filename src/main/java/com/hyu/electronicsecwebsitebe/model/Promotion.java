package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "khuyenmai")
public class Promotion {
    @Id
    private String id;
    private String name;
    private String description;
    private int discountPercentage;
    private Date startDate;
    private Date endDate;
}
