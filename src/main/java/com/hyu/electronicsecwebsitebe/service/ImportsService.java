package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Imports;

import java.util.List;

public interface ImportsService {
    List<Imports> getAllImports();

    Imports findById(String id);

    Imports saveImports(Imports imports);

    Imports updateImports(Imports imports);

    boolean existsById(String id);

    void deleteImports(String id);
}
