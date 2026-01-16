package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Imports;
import com.hyu.electronicsecwebsitebe.repository.ImportsRepository;
import com.hyu.electronicsecwebsitebe.service.ImportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportsServiceImpl implements ImportsService {
    @Autowired
    private ImportsRepository importsRepository;

    @Override
    public List<Imports> getAllImports() {
        return importsRepository.findAll ();
    }
}
