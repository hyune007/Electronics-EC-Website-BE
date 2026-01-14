package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.ProductCategory;
import com.hyu.electronicsecwebsitebe.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/all")
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getProductCategoryRepository();
    }

}
