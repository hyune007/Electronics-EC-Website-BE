package com.hyu.electronicsecwebsitebe.controller;
//Role

import com.hyu.electronicsecwebsitebe.model.Role;
import com.hyu.electronicsecwebsitebe.service.impl.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleServiceImpl roleService;

    @GetMapping("/all")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles ();
    }

    @GetMapping("/{id}")
    public Role findById(@PathVariable String id) {
        return roleService.findById (id);
    }

    @PostMapping("/save")
    public Role saveRole(@RequestBody Role role) {
        return roleService.saveRole (role);
    }

    @PutMapping("/update/{id}")
    public Role updateRole(@PathVariable String id, @RequestBody Role role) {
        role.setId (id);
        return roleService.updateRole (role);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable String id) {
        roleService.deleteById (id);
    }
}
