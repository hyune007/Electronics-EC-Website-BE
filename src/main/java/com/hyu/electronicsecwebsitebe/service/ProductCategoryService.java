package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> getAllProductCategories();

    ProductCategory findById(String id);

    ProductCategory saveProductCategory(ProductCategory productCategory);

    ProductCategory updateProductCategory(ProductCategory productCategory);

    boolean existsById(String id);

    void deleteById(String id);
}
