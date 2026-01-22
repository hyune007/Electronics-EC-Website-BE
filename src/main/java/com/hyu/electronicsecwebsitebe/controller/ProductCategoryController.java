package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.ProductCategory;
import com.hyu.electronicsecwebsitebe.service.impl.ProductCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-category")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @GetMapping("/all")
    public List<ProductCategory> getAllProductCategories() {
        return productCategoryService.getAllProductCategories ();
    }

    @GetMapping("/{id}")
    public ProductCategory getProductCategoryById(@PathVariable String id) {
        return productCategoryService.findById (id);
    }

    @PostMapping("/save")
    public ProductCategory saveProductCategory(@RequestBody ProductCategory productCategory) {
        return productCategoryService.saveProductCategory (productCategory);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id) {
        productCategoryService.deleteById (id);
    }
}
