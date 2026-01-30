package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface ProductService {
    Page<Product> getProducts(Pageable pageable, String categoryId, String brandId, String keyword, BigDecimal minPrice, BigDecimal maxPrice);

    Product findById(String id);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteById(String id);

    boolean existsById(String id);
}
