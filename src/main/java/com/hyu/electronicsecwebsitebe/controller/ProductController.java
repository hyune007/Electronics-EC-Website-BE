package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.Product;
import com.hyu.electronicsecwebsitebe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(defaultValue = "0") int p,
                                                     @RequestParam(required = false) String category,
                                                     @RequestParam(required = false) String q) {
        Pageable pageable = PageRequest.of (p, 10);
        Page<Product> products = productService.getProducts (pageable, category, q);
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

    @PostMapping("/save")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (productService.existsById (product.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
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
