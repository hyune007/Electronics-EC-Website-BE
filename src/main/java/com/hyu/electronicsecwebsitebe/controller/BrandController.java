package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.Brand;
import com.hyu.electronicsecwebsitebe.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//huynt
import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandServiceImpl brandService;

    @GetMapping("/all")
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> listBrands = brandService.getAllBrands ();
        return ResponseEntity.ok (listBrands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable String id) {
        if (!brandService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        Brand foundBrand = brandService.findById (id);
        return ResponseEntity.ok (foundBrand);
    }

    @PostMapping("/save")
    public ResponseEntity<Brand> saveBrand(@RequestBody Brand brand) {
        if (brandService.existsById (brand.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        Brand savedBrand = brandService.saveBrand (brand);
        return ResponseEntity.status (HttpStatus.CREATED).body (savedBrand);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable String id, @RequestBody Brand brand) {
        if (!brandService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        brand.setId (id);
        Brand updatedBrand = brandService.updateBrand (brand);
        return ResponseEntity.ok (updatedBrand);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable String id) {
        if (!brandService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        brandService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }
}
