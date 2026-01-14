package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.ProductCategory;
import com.hyu.electronicsecwebsitebe.repository.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductCategoryService {
    ProductCategory createProductCategory(ProductCategory productCategory);

    List<ProductCategory> getAllProductCategories();

}
