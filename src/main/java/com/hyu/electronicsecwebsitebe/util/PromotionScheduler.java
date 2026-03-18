package com.hyu.electronicsecwebsitebe.util;

import com.hyu.electronicsecwebsitebe.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class PromotionScheduler {

    private final ProductRepository productRepository;

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    public void clearExpiredPromotions() {
        productRepository.removeExpiredPromotions(Instant.now());
    }
}
