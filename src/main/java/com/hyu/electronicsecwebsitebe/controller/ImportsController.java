package com.hyu.electronicsecwebsitebe.controller;

import com.hyu.electronicsecwebsitebe.model.Imports;
import com.hyu.electronicsecwebsitebe.service.impl.ImportsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/imports")
public class ImportsController {
    @Autowired
    private ImportsServiceImpl importsService;

    @GetMapping("/all")
    public List<Imports> getAllImports() {
        return importsService.getAllImports ();
    }
}
