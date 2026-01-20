package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Product;
import com.hyu.electronicsecwebsitebe.repository.ProductRepository;
import com.hyu.electronicsecwebsitebe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getProducts(Pageable pageable, String categoryId, String keyword) {
        if(categoryId==null&&(keyword==null||keyword.trim().isEmpty())){
            return productRepository.findAll(pageable);
        }
        if(categoryId==null){
            return productRepository.findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(keyword, keyword, pageable);
        }
        if(keyword==null||keyword.trim().isEmpty()){
            return productRepository.findByCategory_Id(categoryId, pageable);
        }
        return productRepository.findByCategoryAndKeyword(categoryId, keyword, pageable);
    }

    @Override
    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return productRepository.existsById(id);
    }
}
