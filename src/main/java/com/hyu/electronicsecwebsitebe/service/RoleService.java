package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    Role findById(String id);

    Role saveRole(Role role);

    Role updateRole(Role role);

    boolean existsById(String id);

    void deleteById(String id);
}
