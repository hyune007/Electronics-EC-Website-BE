package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.Product;
import com.hyu.electronicsecwebsitebe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(defaultValue = "0") int p,
                                                     @RequestParam(required = false) String category,
                                                     @RequestParam(required = false) List<String> brand,
                                                     @RequestParam(required = false) String q,
                                                     @RequestParam(required = false) List<String> priceRanges,
                                                     @RequestParam(defaultValue = "0") BigDecimal minPrice,
                                                     @RequestParam(defaultValue = "0") BigDecimal maxPrice,
                                                     @RequestParam(required = false) String priceSort) {
        if(minPrice.compareTo(maxPrice) > 0) {
            return ResponseEntity.badRequest ().body(Page.empty());
        }
        Sort sort = Sort.unsorted();

        if ("asc".equalsIgnoreCase(priceSort)) {
            sort = Sort.by("price").ascending();
        } else if ("desc".equalsIgnoreCase(priceSort)) {
            sort = Sort.by("price").descending();
        }
        Pageable pageable = PageRequest.of (p, 12, sort);
        Page<Product> products = productService.getProducts (pageable, category, brand, q, priceRanges, minPrice, maxPrice);
        return ResponseEntity.ok (products);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        if (!productService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        Product product = productService.findById (id);
        return ResponseEntity.ok (product);
    }
//,@RequestParam("photo") MultipartFile file
    @PostMapping("/save")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (productService.existsById (product.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
//        try {
//            productService.uploadPhoto (product, file);
//        }
//        catch (Exception e) {
//            return ResponseEntity.badRequest ().build ();
//        }
        Product createdProduct = productService.createProduct (product);
        return ResponseEntity.status (HttpStatus.CREATED).body (createdProduct);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product product) {
        if (!productService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        product.setId (id);
        Product updatedProduct = productService.updateProduct (product);
        return ResponseEntity.ok (updatedProduct);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        if (!productService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        productService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }
}
