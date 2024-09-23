package com.ecommerce.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.api.dtos.CustomerDTO;
import com.ecommerce.api.entities.Customer;
import com.ecommerce.api.repositories.CustomerRepository;


@Service
public class CustomerService {

	private final CustomerRepository customerRepository;
	
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}
	
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    public Optional<CustomerDTO> getCustomerById(Long id) {
        return customerRepository.findById(id)
            .map(this::convertToDTO); 
    }

    private CustomerDTO convertToDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setMail(customer.getMail());
        dto.setPhone(customer.getPhone());
        dto.setIdentityNumber(customer.getIdentityNumber());
        return dto;
    }
}
