package com.ecommerce.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.api.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long>{

	Optional<Customer> findByIdentityNumber(String identityNumber);
}
