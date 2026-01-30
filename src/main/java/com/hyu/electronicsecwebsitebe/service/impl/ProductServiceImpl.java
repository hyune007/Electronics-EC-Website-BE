package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Product;
import com.hyu.electronicsecwebsitebe.repository.ProductRepository;
import com.hyu.electronicsecwebsitebe.service.ProductService;
import com.hyu.electronicsecwebsitebe.util.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getProducts(Pageable pageable, String categoryId, String brandId, String keyword, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> spec = Specification
                .where(ProductSpecification.hasCategory(categoryId))
                .and(ProductSpecification.hasBrand(brandId))
                .and(ProductSpecification.hasKeyword(keyword))
                .and(ProductSpecification.priceBetween(minPrice, maxPrice));

        return productRepository.findAll(spec, pageable);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById (id).orElse (null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save (product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save (product);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById (id);
    }

    @Override
    public boolean existsById(String id) {
        return productRepository.existsById (id);
    }
}
