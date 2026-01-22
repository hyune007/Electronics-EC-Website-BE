package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.Review;
import com.hyu.electronicsecwebsitebe.service.impl.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewServiceImpl reviewService;

    @GetMapping("/all")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> listReviews = reviewService.getAllReviews ();
        return ResponseEntity.ok (listReviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable String id) {
        if (!reviewService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        Review foundReview = reviewService.findById (id);
        return ResponseEntity.ok (foundReview);
    }

    @GetMapping("/{id}/product/{productId}")
    public ResponseEntity<Review> getReviewByProductId(@PathVariable String id, @PathVariable String productId) {
        Review foundReview = reviewService.findByIdAndProductId (id, productId);
        if (foundReview == null) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (foundReview);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Review> getReviewByProductId(@PathVariable String productId) {
        Review foundReview = reviewService.findByProductId (productId);
        if (foundReview == null) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (foundReview);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Review> getReviewByCustomerId(@PathVariable String customerId) {
        Review foundReview = reviewService.findByCustomerId (customerId);
        if (foundReview == null) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (foundReview);
    }

    @GetMapping("/customer/{customerId}/product/{productId}")
    public ResponseEntity<Review> getReviewByCustomerIdAndProductId(@PathVariable String customerId, @PathVariable String productId) {
        Review foundReview = reviewService.findByCustomerIdAndProductId (customerId, productId);
        if (foundReview == null) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (foundReview);
    }

    @PostMapping("/save")
    public ResponseEntity<Review> saveReview(@RequestBody Review review) {
        if (reviewService.existsById (review.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        Review savedReview = reviewService.saveReview (review);
        return ResponseEntity.status (201).body (savedReview);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody Review review) {
        if (!reviewService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        review.setId (id);
        Review updatedReview = reviewService.updateReview (id, review);
        return ResponseEntity.ok (updatedReview);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        if (!reviewService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        reviewService.deleteReview (id);
        return ResponseEntity.noContent ().build ();
    }
}
