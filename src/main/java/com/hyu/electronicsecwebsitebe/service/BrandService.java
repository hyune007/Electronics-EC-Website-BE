package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();

    Brand findById(String id);

    Brand saveBrand(Brand brand);

    Brand updateBrand(Brand brand);

    boolean existsById(String id);

    void deleteById(String id);
}
