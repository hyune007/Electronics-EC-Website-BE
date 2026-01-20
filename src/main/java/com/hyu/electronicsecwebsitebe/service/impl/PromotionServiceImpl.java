package com.hyu.electronicsecwebsitebe.service.impl;


import com.hyu.electronicsecwebsitebe.model.Promotion;
import com.hyu.electronicsecwebsitebe.repository.PromotionRepository;
import com.hyu.electronicsecwebsitebe.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll ();
    }

    @Override
    public Promotion findById(String id) {
        return promotionRepository.findById (id).orElse (null);
    }

    @Override
    public Promotion savePromotion(Promotion promotion) {
        return promotionRepository.save (promotion);
    }

    @Override
    public Promotion updatePromotion(Promotion promotion) {
        return promotionRepository.save (promotion);
    }

    @Override
    public void deletePromotion(String id) {
        promotionRepository.deleteById (id);
    }
}
