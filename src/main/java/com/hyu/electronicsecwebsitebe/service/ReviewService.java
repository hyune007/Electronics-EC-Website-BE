package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Review;

import java.util.List;

public interface ReviewService {
    List<Review> getAllReviews();

    Review findById(String id);

    Review findByProductId(String productId);

    Review findByCustomerId(String customerId);

    Review findByCustomerIdAndProductId(String customerId, String productId);

    Review findByIdAndProductId(String id, String productId);

    Review saveReview(Review review);

    Review updateReview(String id, Review review);

    boolean existsById(String id);

    void deleteReview(String id);
}
