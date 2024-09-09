package com.ecommerce.api.services;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ecommerce.api.repositories.CategoryRepository;
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
	public List<Category> findAll(){
		return this.categoryRepository.findAll();
	}
	
}
