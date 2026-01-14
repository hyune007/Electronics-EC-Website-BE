package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "nhapkho")
public class Imports {
    @Id
    private String id;
    private int quantity;
    private Date importDate;
}
