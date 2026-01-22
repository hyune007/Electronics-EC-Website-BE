package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.ProductCategory;
import com.hyu.electronicsecwebsitebe.repository.ProductCategoryRepository;
import com.hyu.electronicsecwebsitebe.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryRepository prodcutCategoryRepository;

    @Override
    public List<ProductCategory> getAllProductCategories() {
        return prodcutCategoryRepository.findAll ();
    }

    @Override
    public ProductCategory findById(String id) {
        return prodcutCategoryRepository.findById (id).orElse (null);
    }

    @Override
    public ProductCategory saveProductCategory(ProductCategory productCategory) {
        return prodcutCategoryRepository.save (productCategory);
    }

    @Override
    public ProductCategory updateProductCategory(ProductCategory productCategory) {
        return prodcutCategoryRepository.save (productCategory);
    }

    @Override
    public boolean existsById(String id) {
        return prodcutCategoryRepository.existsById (id);
    }

    @Override
    public void deleteById(String id) {
        prodcutCategoryRepository.deleteById (id);
    }
}
