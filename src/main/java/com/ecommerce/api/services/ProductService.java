package com.ecommerce.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.ecommerce.api.dtos.CategoryDTO;
import com.ecommerce.api.dtos.ProductDTO;
import com.ecommerce.api.entities.Product;
import com.ecommerce.api.repositories.ProductRepository;

import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	
	public Page<ProductDTO> getAllProducts(Pageable pageable) {
	    return productRepository.findAll(pageable).map(this::convertToDTO);
	}

	public ProductDTO getProductById(Long id) {
	    Optional<Product> productOptional = productRepository.findById(id);
	    return productOptional.map(this::convertToDTO).orElse(null);
	}

	public Page<ProductDTO> getProductsByDescription(String description, Pageable pageable) {
	    return productRepository.findByDescriptionContainingIgnoreCase(description, pageable).map(this::convertToDTO);
	}

	private ProductDTO convertToDTO(Product product) {
	    ProductDTO dto = new ProductDTO();
	    dto.setId(product.getId());
	    dto.setName(product.getName());
	    dto.setPrice(product.getPrice());
	    dto.setDescription(product.getDescription());

	    if (product.getCategory() != null) {
	        CategoryDTO categoryDTO = new CategoryDTO();
	        categoryDTO.setId(product.getCategory().getId());
	        categoryDTO.setName(product.getCategory().getName());
	        dto.setCategory(categoryDTO);
	    }

	    return dto;
	}
}
