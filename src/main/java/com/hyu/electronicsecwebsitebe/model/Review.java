package com.hyu.electronicsecwebsitebe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "danhgia")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Review {
    @Id
    @Column(name = "dg_id")
    private String id;

    @Column(name = "dg_content", length = 100)
    private String content;

    @Column(name = "dg_rating")
    private int rating;

    @Column(name = "dg_date")
    @Temporal(TemporalType.DATE)
    private Date reviewDate;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    @JsonIgnoreProperties({ "category", "brand" })
    private Product product;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    @JsonIgnoreProperties({ "password", "role" })
    private Customer customer;
}
