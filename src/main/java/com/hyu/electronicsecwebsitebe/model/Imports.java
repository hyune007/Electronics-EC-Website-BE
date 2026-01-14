package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "nhapkho")
public class Imports {
    @Id
    @Column(name = "nk_id")
    private String id;

    @Column(name = "nk_quantity")
    private int quantity;

    @Column(name = "nk_date")
    @Temporal(TemporalType.DATE)
    private Date importDate;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;
}
