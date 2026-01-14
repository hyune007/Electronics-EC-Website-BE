package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "giohang", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"kh_id", "sp_id"})
})
public class ShoppingCart {
    @Id
    @Column(name = "gh_id")
    private String id;

    @Column(name = "sp_quantity")
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "kh_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "sp_id")
    private Product product;
}
