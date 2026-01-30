package com.hyu.electronicsecwebsitebe.util;

import com.hyu.electronicsecwebsitebe.model.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String categoryId) {
        return (root, query, cb) ->
                categoryId == null ? cb.conjunction()
                        : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasBrand(String brandId) {
        return (root, query, cb) ->
                brandId == null ? cb.conjunction()
                        : cb.equal(root.get("brand").get("id"), brandId);
    }

    public static Specification<Product> hasKeyword(String keyword) {
        return (root, query, cb) ->
                (keyword == null || keyword.trim().isEmpty())
                        ? cb.conjunction()
                        : cb.like(
                        cb.lower(root.get("name")),
                        "%" + keyword.toLowerCase() + "%"
                );
    }

    public static Specification<Product> priceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice
    ) {
        return (root, query, cb) -> {
            if(minPrice.compareTo(BigDecimal.ZERO) > 0 || maxPrice.compareTo(BigDecimal.ZERO) > 0){
                return cb.between(root.get("price"), minPrice, maxPrice);
            }
            return cb.conjunction();
        };
    }
}
