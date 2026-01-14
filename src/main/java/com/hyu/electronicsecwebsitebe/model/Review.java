package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "danhgia")
public class Review {
    @Id
    @Column(name = "dg_id")
    private String id;

    @Column(name = "dg_content")
    private String content;

    @Column(name = "dg_rating")
    private int rating;

    @Column(name = "dg_date")
    @Temporal(TemporalType.DATE)
    private Date reviewDate;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;
}
