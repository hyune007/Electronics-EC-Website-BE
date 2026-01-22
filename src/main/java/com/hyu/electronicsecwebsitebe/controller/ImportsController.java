package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.Imports;
import com.hyu.electronicsecwebsitebe.service.impl.ImportsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/imports")
public class ImportsController {
    @Autowired
    private ImportsServiceImpl importsService;

    @GetMapping("/all")
    public ResponseEntity<List<Imports>> getAll() {
        List<Imports> importsList = importsService.getAllImports ();
        return ResponseEntity.ok (importsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Imports> getImportsById(@PathVariable String id) {
        Imports imports = importsService.findById (id);
        if (imports != null) {
            return ResponseEntity.ok (imports);
        } else {
            return ResponseEntity.notFound ().build ();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Imports> saveImports(@RequestBody Imports imports) {
        if (importsService.existsById (imports.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        Imports savedImports = importsService.saveImports (imports);
        return ResponseEntity.status (HttpStatus.CREATED).body (savedImports);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Imports> updateImports(@PathVariable String id, @RequestBody Imports imports) {
        if (!importsService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        imports.setId (id);
        Imports updatedImports = importsService.updateImports (imports);
        return ResponseEntity.ok (updatedImports);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteImports(@PathVariable String id) {
        if (!importsService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        importsService.deleteImports (id);
        return ResponseEntity.noContent ().build ();
    }
}
