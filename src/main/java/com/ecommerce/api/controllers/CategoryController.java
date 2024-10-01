package com.ecommerce.api.controllers;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.api.services.CategoryService;
import com.ecommerce.api.dtos.CategoryDTO;


@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDTO>> findAll() {
	    List<CategoryDTO> categories = this.categoryService.findAll();

	    if (categories.isEmpty()) {
	        return ResponseEntity.noContent().build(); // 204 
	    } else {
	        return ResponseEntity.ok(categories); // 200 OK 
	    }
	}
	  
}
