package com.ecommerce.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.api.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
