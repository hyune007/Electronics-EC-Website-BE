package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.Review;
import com.hyu.electronicsecwebsitebe.service.impl.ReviewServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
    @Autowired
    private ReviewServiceImpl reviewService;

    @GetMapping("/all")
    public List<Review> getAllReviews() {
        return reviewService.getAllReviews ();
    }

    @GetMapping("/{id}")
    public Review findById(@PathVariable String id) {
        return reviewService.findById (id);
    }

    @GetMapping("/{id}/product/{productId}")
    public Review findByIdAndProductId(@PathVariable String id, @PathVariable
    String productId) {
        return reviewService.findByIdAndProductId (id, productId);
    }

    @GetMapping("/product/{productId}")
    public Review findByProductId(@PathVariable String productId) {
        return reviewService.findByProductId (productId);
    }

    @GetMapping("/customer/{customerId}")
    public Review findByCustomerId(@PathVariable String customerId) {
        return reviewService.findByCustomerId (customerId);
    }

    @GetMapping("/customer/{customerId}/product/{productId}")
    public Review findByCustomerIdAndProductId(@PathVariable String customerId,
                                               @PathVariable String productId) {
        return reviewService.findByCustomerIdAndProductId (customerId, productId);
    }

    @PostMapping("/save")
    public Review saveReview(@RequestBody Review review) {
        return reviewService.saveReview (review);
    }

    @PutMapping("/update/{id}")
    public Review updateReview(@PathVariable String id, @RequestBody Review review) {
        return reviewService.updateReview (id, review);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteReview(@PathVariable String id) {
        reviewService.deleteReview (id);
    }
}
