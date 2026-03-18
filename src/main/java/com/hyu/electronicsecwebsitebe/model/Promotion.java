package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
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
    private Instant startDate;

    @Column(name = "km_end_date")
    private Instant endDate;

    public boolean isActive() {
        Instant now = Instant.now();
        return (startDate == null || !startDate.isAfter(now))
                && (endDate == null || !endDate.isBefore(now));
    }
}
