package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Bill;
import com.hyu.electronicsecwebsitebe.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {

    List<Bill> findByCustomerId(String customerId);

}
