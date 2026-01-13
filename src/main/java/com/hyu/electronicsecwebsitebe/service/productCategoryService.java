package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.productCategory;
import com.hyu.electronicsecwebsitebe.repository.productCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productCategoryService {

    @Autowired
    private productCategoryRepository productCategoryRepository;

    public List<productCategory> getProductCategoryRepository() {
        return productCategoryRepository.findAll();
    }

}
