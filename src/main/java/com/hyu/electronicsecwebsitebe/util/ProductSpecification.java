package com.hyu.electronicsecwebsitebe.util;

import com.hyu.electronicsecwebsitebe.model.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductSpecification {

    public static Specification<Product> hasCategory(String categoryId) {
        return (root, query, cb) ->
                categoryId == null ? cb.conjunction()
                        : cb.equal(root.get("category").get("id"), categoryId);
    }

    public static Specification<Product> hasBrands(List<String> brandIds) {
        return (root, query, cb) ->
        {
            if (brandIds == null || brandIds.isEmpty()) {
                return cb.conjunction();
            }
            return root.get("brand").get("id").in(brandIds);
        };
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

    public static Specification<Product> priceInRanges(List<String> priceRanges) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (String r : priceRanges) {
                String[] p = r.split("-");
                BigDecimal min = new BigDecimal(p[0]);
                BigDecimal max = new BigDecimal(p[1]);
                predicates.add(cb.between(root.get("price"), min, max));
            }

            return cb.or(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> hasStock() {
        return (root, query, cb) -> cb.greaterThan(root.get("stock"), 0);
    }

}
