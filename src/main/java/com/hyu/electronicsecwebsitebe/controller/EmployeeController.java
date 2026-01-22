package com.hyu.electronicsecwebsitebe.controller;
//thinhlk

import com.hyu.electronicsecwebsitebe.model.Employee;
import com.hyu.electronicsecwebsitebe.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/page/{p}/keywork/{q}")
    public ResponseEntity<Page<Employee>> getEmployees(@PathVariable int p, @PathVariable String q) {
        Pageable pageable = PageRequest.of (p, 10);
        Page<Employee> employees = employeeService.getEmployees (pageable, q);
        return ResponseEntity.ok (employees);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        if (!employeeService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        Employee employee = employeeService.findById (id);
        return ResponseEntity.ok (employee);
    }

    @PostMapping("/save")
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        if (employeeService.existsById (employee.getId ())) {
            return ResponseEntity.badRequest ().build ();
        }
        Employee createdEmployee = employeeService.createEmployee (employee);
        return ResponseEntity.status (HttpStatus.CREATED).body (createdEmployee);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        if (!employeeService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        employee.setId (id);
        Employee updatedEmployee = employeeService.updateEmployee (employee);
        return ResponseEntity.ok (updatedEmployee);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        if (!employeeService.existsById (id)) {
            return ResponseEntity.notFound ().build ();
        }
        employeeService.deleteById (id);
        return ResponseEntity.noContent ().build ();
    }
}
