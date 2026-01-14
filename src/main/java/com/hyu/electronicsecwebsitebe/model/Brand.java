package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "hang")
public class Brand {
    @Id
    @JoinColumn(name = "hang_id")
    private String id;

    @JoinColumn(name = "hang_name")
    private String name;
}
