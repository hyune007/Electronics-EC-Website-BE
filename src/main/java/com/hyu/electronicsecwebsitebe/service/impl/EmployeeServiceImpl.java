package com.hyu.electronicsecwebsitebe.service.impl;

import com.hyu.electronicsecwebsitebe.model.Employee;
import com.hyu.electronicsecwebsitebe.repository.EmployeeRepository;
import com.hyu.electronicsecwebsitebe.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final String ID_PREFIX = "NV";
    private static final int ID_NUMBER_LENGTH = 3;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Page<Employee> getEmployees(Pageable pageable, String keyword, String roleId) {
        if (roleId == null && (keyword == null || keyword.trim ().isEmpty ())) {
            return employeeRepository.findAll (pageable);
        }
        if (roleId == null) {
            return employeeRepository.findByIdContainingIgnoreCaseOrNameContainingIgnoreCase (keyword, keyword, pageable);
        }
        if (keyword == null || keyword.trim ().isEmpty ()) {
            return employeeRepository.findByRoleId (roleId, pageable);
        }
        return employeeRepository.findByRoleAndKeyword (roleId, keyword, pageable);
    }

    @Override
    public Employee findById(String id) {
        return employeeRepository.findById (id).orElse (null);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employee.getId () == null || employee.getId ().isEmpty ()) {
            employee.setId (generateNextId ());
        }
        if (employee.getPassword () != null && !employee.getPassword ().isEmpty ()) {
            String hashedPassword = passwordEncoder.encode (employee.getPassword ());
            employee.setPassword (hashedPassword);
        }
        return employeeRepository.save (employee);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save (employee);
    }

    @Override
    public void deleteById(String id) {
        employeeRepository.deleteById (id);
    }

    @Override
    public boolean existsById(String id) {
        return employeeRepository.existsById (id);
    }

    @Override
    public String generateNextId() {
        String maxId = employeeRepository.findMaxId ();
        int nextNumber = 1;
        if (maxId != null && maxId.startsWith (ID_PREFIX)) {
            try {
                String numberPart = maxId.substring (ID_PREFIX.length ());
                nextNumber = Integer.parseInt (numberPart) + 1;
            } catch (NumberFormatException ignored) {
            }
        }
        return ID_PREFIX + String.format ("%0" + ID_NUMBER_LENGTH + "d", nextNumber);
    }
}
