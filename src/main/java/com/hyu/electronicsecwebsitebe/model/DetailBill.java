package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "chitiethoadon")
public class DetailBill {
    @Id
    @Column(name = "hdct_id")
    private String id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "hdct_total")
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "hd_id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;
}
