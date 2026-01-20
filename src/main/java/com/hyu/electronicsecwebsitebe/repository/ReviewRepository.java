package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Review findByIdAndProductId(String id, String productId);

    Review findByProductId(String productId);

    Review findByCustomerId(String customerId);

    Review findByCustomerIdAndProductId(String customerId, String productId);
}
