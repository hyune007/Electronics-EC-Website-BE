package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Employee;
import com.hyu.electronicsecwebsitebe.repository.EmployeeRepository;
import com.hyu.electronicsecwebsitebe.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Page<Employee> getEmployees(Pageable pageable, String keyword) {
        if(keyword==null||keyword.trim().isEmpty()){
            return employeeRepository.findAll(pageable);
        }
        return employeeRepository.findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(keyword, keyword, pageable);
    }

    @Override
    public Employee findById(String id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteById(String id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public boolean existsById(String id) {
        return employeeRepository.existsById(id);
    }
}
