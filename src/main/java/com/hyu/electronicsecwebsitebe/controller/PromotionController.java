package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.Promotion;
import com.hyu.electronicsecwebsitebe.service.impl.PromotionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotion")
public class PromotionController {
    @Autowired
    private PromotionServiceImpl promotionService;

    @GetMapping("/all")
    public List<Promotion> getAllPromotion() {
        return promotionService.getAllPromotions ();
    }

    @GetMapping("/{id}")
    public Promotion getPromotionById(@PathVariable String id) {
        return promotionService.findById (id);
    }

    @PostMapping("/save")
    public Promotion savePromotion(@RequestBody Promotion promotion) {
        return promotionService.savePromotion (promotion);
    }

    @PutMapping("/update")
    public Promotion updatePromotion(@RequestBody Promotion promotion) {
        return promotionService.updatePromotion (promotion);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePromotion(@PathVariable String id) {
        promotionService.deletePromotion (id);
    }
}
