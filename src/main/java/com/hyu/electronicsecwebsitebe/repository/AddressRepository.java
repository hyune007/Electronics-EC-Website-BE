package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    boolean existsByCustomer_IdAndCityAndWardAndDetailAddress(String customerId, String city, String ward, String detailAddress);

    List<Address> findByCustomer_Id(String customerId);

    long countByCustomer_Id(String customerId);
}
