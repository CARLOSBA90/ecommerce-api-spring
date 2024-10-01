package com.ecommerce.api.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
	public ResponseEntity<Page<CustomerDTO>> findAll(
		    @RequestParam(defaultValue = "0") int page,
		    @RequestParam(defaultValue = "10") int size) {
		
	    Pageable pageable = PageRequest.of(page, size);
	    Page<CustomerDTO> pagedResult = customerService.getAllCustomers(pageable);

	    if (pagedResult.isEmpty()) {
	        return ResponseEntity.noContent().build(); // 204 
	    } else {
	        return ResponseEntity.ok(pagedResult); // 200 OK 
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
