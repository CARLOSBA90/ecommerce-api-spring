package com.ecommerce.api.services;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.ecommerce.api.repositories.CategoryRepository;
import com.ecommerce.api.dtos.CategoryDTO;
import com.ecommerce.api.entities.Category;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository){
		this.categoryRepository = categoryRepository;
	}
	
	/**
	 * List of Entity
	 */
	public List<CategoryDTO> findAll(){
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	
	private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
