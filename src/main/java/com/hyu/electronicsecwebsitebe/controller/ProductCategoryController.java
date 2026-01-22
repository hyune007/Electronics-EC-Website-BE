package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.ProductCategory;
import com.hyu.electronicsecwebsitebe.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-category")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductCategory>> getAllProductCategories() {
        List<ProductCategory> listProductCategories = productCategoryService.getAllProductCategories ();
        return ResponseEntity.ok (listProductCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable String id) {
        if (!productCategoryService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        ProductCategory foundProductCategory = productCategoryService.findById (id);
        return ResponseEntity.ok (foundProductCategory);
    }

    @PostMapping("/save")
    public ResponseEntity<ProductCategory> saveProductCategory(@RequestBody ProductCategory productCategory) {
        if (productCategoryService.existsById (productCategory.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        ProductCategory savedProductCategory = productCategoryService.saveProductCategory (productCategory);
        return ResponseEntity.status (HttpStatus.CREATED).body (savedProductCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable String id) {
        if (!productCategoryService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        productCategoryService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }
}
