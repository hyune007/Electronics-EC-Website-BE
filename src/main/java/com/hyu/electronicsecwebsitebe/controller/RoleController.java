package com.hyu.electronicsecwebsitebe.controller;
//huynt

import com.hyu.electronicsecwebsitebe.model.Role;
import com.hyu.electronicsecwebsitebe.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> listRoles = roleService.getAllRoles ();
        return ResponseEntity.ok (listRoles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable String id) {
        if (!roleService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        Role foundRole = roleService.findById (id);
        return ResponseEntity.ok (foundRole);
    }

    @PostMapping("/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        if (roleService.existsById (role.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        Role savedRole = roleService.saveRole (role);
        return ResponseEntity.status (HttpStatus.CREATED).body (savedRole);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable String id, @RequestBody Role role) {
        if (!roleService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        role.setId (id);
        Role updatedRole = roleService.updateRole (role);
        return ResponseEntity.ok (updatedRole);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable String id) {
        if (!roleService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        roleService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }
}
