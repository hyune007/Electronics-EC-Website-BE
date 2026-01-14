package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "khuyenmai")
public class Promotion {
    @Id
    @Column(name = "km_id")
    private String id;

    @Column(name = "km_name")
    private String name;

    @Column(name = "km_description")
    private String description;

    @Column(name = "km_percent")
    private int discountPercentage;

    @Column(name = "km_start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "km_end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
}
