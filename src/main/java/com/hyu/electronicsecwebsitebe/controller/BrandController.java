package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.Brand;
import com.hyu.electronicsecwebsitebe.service.impl.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandServiceImpl brandService;

    @GetMapping("/all")
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands ();
    }

    @GetMapping("/{id}")
    public Brand getBrandById(@PathVariable String id) {
        return brandService.findById (id);
    }

    @PostMapping("/save")
    public Brand saveBrand(@RequestBody Brand brand) {
        return brandService.saveBrand (brand);
    }

    @PutMapping("/update/{id}")
    public Brand updateBrand(@PathVariable String id, @RequestBody Brand brand) {
        brand.setId (id);
        return brandService.updateBrand (brand);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBrandById(@PathVariable String id) {
        brandService.deleteById (id);
    }
}
