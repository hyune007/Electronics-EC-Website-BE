package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Review findByIdAndProductId(String id, String productId);

    List<Review> findAllByProductId(String productId);

    List<Review> findAllByCustomerId(String customerId);

    Review findByCustomerIdAndProductId(String customerId, String productId);
}
