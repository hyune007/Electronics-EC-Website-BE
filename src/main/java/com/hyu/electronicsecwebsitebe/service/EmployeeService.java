package com.hyu.electronicsecwebsitebe.service;

import com.hyu.electronicsecwebsitebe.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Page<Employee> getEmployees(Pageable pageable, String keyword, String roleId);

    Employee findById(String id);

    Employee createEmployee(Employee employee);

    Employee updateEmployee(Employee employee);

    void deleteById(String id);

    boolean existsById(String id);
}
