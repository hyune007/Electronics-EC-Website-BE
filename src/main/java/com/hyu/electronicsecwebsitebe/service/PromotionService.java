package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Promotion;

import java.util.List;

public interface PromotionService {
    List<Promotion> getAllPromotions();

    Promotion findById(String id);

    Promotion savePromotion(Promotion promotion);

    Promotion updatePromotion(Promotion promotion);

    boolean existsById(String id);

    void deletePromotion(String id);
}
