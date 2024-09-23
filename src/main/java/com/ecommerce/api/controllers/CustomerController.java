package com.ecommerce.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.api.dtos.CustomerDTO;
import com.ecommerce.api.services.CustomerService;


@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	private final CustomerService customerService;
	
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<CustomerDTO>> findAll() {
	    List<CustomerDTO> categories = this.customerService.getAllCustomers();

	    if (categories.isEmpty()) {
	        return ResponseEntity.noContent().build(); // 204 
	    } else {
	        return ResponseEntity.ok(categories); // 200 OK 
	    }
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CustomerDTO> findById(@PathVariable Long id) {
	    Optional<CustomerDTO> customerDTO = this.customerService.getCustomerById(id);

	    if (customerDTO.isPresent()) {
	        return ResponseEntity.ok(customerDTO.get()); // 200 OK 
	    } else {
	        return ResponseEntity.notFound().build(); // 404 Not Found
	    }
	}

}
