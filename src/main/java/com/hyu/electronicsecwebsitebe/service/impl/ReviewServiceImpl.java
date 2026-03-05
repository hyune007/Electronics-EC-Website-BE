package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Review;
import com.hyu.electronicsecwebsitebe.repository.ReviewRepository;
import com.hyu.electronicsecwebsitebe.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review findById(String id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review> findAllByProductId(String productId) {
        return reviewRepository.findAllByProductId(productId);
    }

    @Override
    public List<Review> findAllByCustomerId(String customerId) {
        return reviewRepository.findAllByCustomerId(customerId);
    }

    @Override
    public Review findByCustomerIdAndProductId(String customerId, String productId) {
        return reviewRepository.findByCustomerIdAndProductId(customerId, productId);
    }

    @Override
    public Review findByIdAndProductId(String id, String productId) {
        return reviewRepository.findByIdAndProductId(id, productId);
    }

    @Override
    public Review saveReview(Review review) {
        prepareDefaults(review);
        return reviewRepository.save(review);
    }

    @Override
    public Review updateReview(Review review) {
        prepareDefaults(review);
        return reviewRepository.save(review);
    }

    @Override
    public boolean existsById(String id) {
        return reviewRepository.existsById(id);
    }

    @Override
    public void deleteReview(String id) {
        reviewRepository.deleteById(id);
    }

    private void prepareDefaults(Review review) {
        if (review.getId() == null || review.getId().isBlank()) {
            review.setId(generateReviewId());
        }
        if (review.getReviewDate() == null) {
            review.setReviewDate(new Date());
        }

        if (review.getContent() != null) {
            String trimmed = review.getContent().trim();
            review.setContent(trimmed.length() > 100 ? trimmed.substring(0, 100) : trimmed);
        }

        int rating = review.getRating();
        if (rating < 1) {
            review.setRating(1);
        } else if (rating > 5) {
            review.setRating(5);
        }
    }

    private String generateReviewId() {
        return "DG" + UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }
}
