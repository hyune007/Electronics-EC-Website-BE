package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.Imports;
import com.hyu.electronicsecwebsitebe.service.impl.ImportsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public Imports getImportsById(@PathVariable String id) {
        return importsService.findById (id);
    }

    @PostMapping("/save")
    public Imports saveImports(@RequestBody Imports imports) {
        return importsService.saveImports (imports);
    }

    @PutMapping("/update")
    public Imports updateImports(@RequestBody Imports imports) {
        return importsService.updateImports (imports);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteImports(@PathVariable String id) {
        importsService.deleteImports (id);
    }
}
