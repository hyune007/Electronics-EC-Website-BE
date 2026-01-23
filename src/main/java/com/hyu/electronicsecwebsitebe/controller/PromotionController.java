package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.Promotion;
import com.hyu.electronicsecwebsitebe.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {
    @Autowired
    private PromotionService promotionService;

    @GetMapping("/all")
    public ResponseEntity<List<Promotion>> findAll() {
        List<Promotion> promotions = promotionService.getAllPromotions ();
        return ResponseEntity.ok (promotions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotion> getPromotionById(@PathVariable String id) {
        if (!promotionService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        Promotion foundPromotion = promotionService.findById (id);
        return ResponseEntity.ok (foundPromotion);
    }

    @PostMapping("/save")
    public ResponseEntity<Promotion> savePromotion(@RequestBody Promotion promotion) {
        if (promotionService.existsById (promotion.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        Promotion createdPromotion = promotionService.savePromotion (promotion);
        return ResponseEntity.status (HttpStatus.CREATED).body (createdPromotion);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Promotion> updatePromotion(@PathVariable String id, @RequestBody Promotion promotion) {
        if (!promotionService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        promotion.setId (id);
        Promotion updatedPromotion = promotionService.updatePromotion (promotion);
        return ResponseEntity.ok (updatedPromotion);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable String id) {
        if (!promotionService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        promotionService.deletePromotion (id);
        return ResponseEntity.ok ().build ();
    }
}
