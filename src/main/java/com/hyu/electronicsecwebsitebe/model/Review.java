package com.hyu.electronicsecwebsitebe.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Data
@Entity
@Table(name = "danhgia")
public class Review {
    @Id
    private String id;
    private String content;
    private int rating;
    private Date reviewDate;
}
