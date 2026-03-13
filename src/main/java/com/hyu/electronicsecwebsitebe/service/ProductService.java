package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    Page<Product> getProducts(Pageable pageable, String categoryId, List<String> brandIds, String keyword, List<String> priceRanges, BigDecimal minPrice, BigDecimal maxPrice, boolean inStockOnly);

    Product findById(String id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteById(String id);

    boolean existsById(String id);
}
