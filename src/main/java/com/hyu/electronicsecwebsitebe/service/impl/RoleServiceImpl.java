package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Role;
import com.hyu.electronicsecwebsitebe.repository.RoleRepository;
import com.hyu.electronicsecwebsitebe.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll ();
    }

    @Override
    public Role findById(String id) {
        return roleRepository.findById (id).orElse (null);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save (role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepository.save (role);
    }

    @Override
    public boolean existsById(String id) {
        return roleRepository.existsById (id);
    }

    @Override
    public void deleteById(String id) {
        roleRepository.deleteById (id);
    }
}
