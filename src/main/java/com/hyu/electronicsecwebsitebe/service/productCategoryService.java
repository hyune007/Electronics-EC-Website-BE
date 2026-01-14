package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.ProductCategory;
import com.hyu.electronicsecwebsitebe.repository.productCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productCategoryService {

    @Autowired
    private productCategoryRepository productCategoryRepository;

    public List<ProductCategory> getProductCategoryRepository() {
        return productCategoryRepository.findAll();
    }

}
