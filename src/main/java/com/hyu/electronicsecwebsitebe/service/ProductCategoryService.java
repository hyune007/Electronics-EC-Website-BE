package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.ProductCategory;
import com.hyu.electronicsecwebsitebe.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getProductCategoryRepository() {
        return productCategoryRepository.findAll();
    }

}
