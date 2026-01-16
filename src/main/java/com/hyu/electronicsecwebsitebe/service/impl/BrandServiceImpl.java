package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Brand;
import com.hyu.electronicsecwebsitebe.repository.BrandRepository;
import com.hyu.electronicsecwebsitebe.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll ();
    }

    @Override
    public Brand findById(String id) {
        return brandRepository.findById (id).orElse (null);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save (brand);
    }

    @Override
    public Brand updateBrand(Brand brand) {
        return brandRepository.save (brand);
    }

    @Override
    public void deleteById(String id) {
        brandRepository.deleteById (id);
    }
}
