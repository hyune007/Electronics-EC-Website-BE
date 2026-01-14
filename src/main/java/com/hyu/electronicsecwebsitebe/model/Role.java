package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vaitro")
public class Role {
    @Id
    @Column(name = "vaitro_id")
    private String id;

    @Column(name = "vaitro_name")
    private String name;
}
