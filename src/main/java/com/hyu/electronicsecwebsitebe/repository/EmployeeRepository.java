package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Page<Employee> findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(String id, String name, Pageable pageable);
}
