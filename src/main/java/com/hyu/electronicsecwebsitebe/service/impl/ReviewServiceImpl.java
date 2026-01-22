package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Review;
import com.hyu.electronicsecwebsitebe.repository.ReviewRepository;
import com.hyu.electronicsecwebsitebe.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;


    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll ();
    }

    @Override
    public Review findById(String id) {
        return reviewRepository.findById (id).orElse (null);
    }

    @Override
    public Review findByProductId(String productId) {
        return reviewRepository.findByProductId (productId);
    }

    @Override
    public Review findByCustomerId(String customerId) {
        return reviewRepository.findByCustomerId (customerId);
    }

    @Override
    public Review findByCustomerIdAndProductId(String customerId, String productId) {
        return reviewRepository.findByCustomerIdAndProductId (customerId, productId);
    }

    @Override
    public Review findByIdAndProductId(String id, String productId) {
        return reviewRepository.findByIdAndProductId (id, productId);
    }

    @Override
    public Review saveReview(Review review) {
        return reviewRepository.save (review);
    }

    @Override
    public Review updateReview(String id, Review review) {
        return reviewRepository.save (review);
    }

    @Override
    public boolean existsById(String id) {
        return reviewRepository.existsById (id);
    }

    @Override
    public void deleteReview(String id) {
        reviewRepository.deleteById (id);
    }
}
