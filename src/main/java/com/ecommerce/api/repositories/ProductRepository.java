package com.ecommerce.api.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ecommerce.api.entities.Product;


public interface ProductRepository extends JpaRepository<Product,Long>{

    Page<Product> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
}
