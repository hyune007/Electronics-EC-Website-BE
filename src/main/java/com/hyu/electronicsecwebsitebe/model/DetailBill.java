package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "chitiethoadon")
public class DetailBill {
    @Id
    private String id;
    private int quantity;
    private int total;
}
