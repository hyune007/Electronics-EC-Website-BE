package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Page<Employee> findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(String id, String name, Pageable pageable);

    Page<Employee> findByRoleId(String roleId, Pageable pageable);

    @Query("""
                SELECT e FROM Employee e
                WHERE e.role.id = :roleId
                  AND (
                        LOWER(e.id) LIKE LOWER(CONCAT('%', :keyword, '%'))
                     OR LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
                  )
            """)
    Page<Employee> findByRoleAndKeyword(
            @Param("roleId") String roleId,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
