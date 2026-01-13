package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.productCategory;
import com.hyu.electronicsecwebsitebe.service.productCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product-categories")
public class productCategoryController {

    @Autowired
    private productCategoryService productCategoryService;

    @GetMapping("/all")
    public List<productCategory> getAllProductCategories() {
        return productCategoryService.getProductCategoryRepository();
    }

}
