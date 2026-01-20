package com.hyu.electronicsecwebsitebe.repository;

import com.hyu.electronicsecwebsitebe.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Page<Product> findByCategory_Id(String categoryId, Pageable pageable);

    Page<Product> findByIdContainingIgnoreCaseOrNameContainingIgnoreCase(String id, String name, Pageable pageable);

    @Query("""
        SELECT p FROM Product p
        WHERE p.category.id = :categoryId
          AND (
                LOWER(p.id) LIKE LOWER(CONCAT('%', :keyword, '%'))
             OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
          )
    """)
    Page<Product> findByCategoryAndKeyword(
            @Param("categoryId") String categoryId,
            @Param("keyword") String keyword,
            Pageable pageable
    );
}
