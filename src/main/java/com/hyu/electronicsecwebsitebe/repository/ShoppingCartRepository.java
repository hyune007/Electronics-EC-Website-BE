package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, String> {

    @Query("SELECT sc FROM ShoppingCart sc WHERE sc.customer.id = :customerId")
    List<ShoppingCart> findByCustomerId(@Param("customerId") String customerId);

}
