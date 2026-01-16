package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Brand;

import java.util.List;

public interface BrandService {
    List<Brand> getAllBrands();

    Brand findById(String id);

    Brand saveBrand(Brand brand);

    Brand updateBrand(Brand brand);

    void deleteById(String id);
}
