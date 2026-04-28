package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {

    @Query("SELECT b FROM Bill b WHERE b.customer.id = :customerId")
    List<Bill> findByCustomerId(@Param("customerId") String customerId);

    List<Bill> findByEmployeeId(String employeeId);
}
