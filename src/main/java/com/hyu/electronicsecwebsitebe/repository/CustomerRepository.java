package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByEmail(String email);

    @Query("SELECT MAX(c.id) FROM Customer c WHERE c.id LIKE 'KH%'")
    String findMaxId();
}
