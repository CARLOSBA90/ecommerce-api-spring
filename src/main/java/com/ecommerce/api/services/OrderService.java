package com.ecommerce.api.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ecommerce.api.dtos.CustomerDTO;
import com.ecommerce.api.dtos.OrderDTO;
import com.ecommerce.api.dtos.OrderItemDTO;
import com.ecommerce.api.dtos.PaymentDTO;
import com.ecommerce.api.entities.Customer;
import com.ecommerce.api.entities.Order;
import com.ecommerce.api.entities.OrderItem;
import com.ecommerce.api.entities.Payment;
import com.ecommerce.api.repositories.OrderRepository;

import java.time.LocalDate;

@Service
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final CustomerService customerService;
	
	public OrderService(OrderRepository orderRepository,
			            CustomerService customerService) {
	  this.orderRepository = orderRepository;
	  this.customerService = customerService;
	}
	
	public Page<OrderDTO> getAllOrders(Pageable pageable) {
	    return orderRepository.findAll(pageable).map(this::convertToDTO);
	}
	
	public OrderDTO getOrderById(Long id) {
	    Optional<Order> orderOptional = orderRepository.findById(id);
	    return orderOptional.map(this::convertToDTO).orElse(null);
	}
	
	public Page<OrderDTO> getOrdersByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
	    return orderRepository.findByOrderDateBetween(startDate.atStartOfDay(), endDate.atTime(23, 59, 59), pageable)
	                          .map(this::convertToDTO);
	}

	public List<OrderDTO> getOrdersByCustomerIdentityNumber(String identityNumber) {
	    Optional<Customer> customerOptional = customerService.findByIdentityNumber(identityNumber); 
	    
	    if (customerOptional.isPresent()) {
	        Customer customer = customerOptional.get();
	        List<Order> orders = orderRepository.findByCustomer(customer);
	        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
	    } else {
	        throw new IllegalArgumentException("Customer not found with identity number: " + identityNumber);
	    }
	}
	

	private OrderDTO convertToDTO(Order order) {
	    OrderDTO dto = new OrderDTO();
	    dto.setId(order.getId());
	    dto.setOrderDate(order.getOrderDate());
	    dto.setOrderStatus(order.getOrderStatus());
	    dto.setTotalAmount(order.getTotalAmount());
	    
	    
	    if (order.getOrderItems() != null) {
	        List<OrderItemDTO> orderItemDTOs = order.getOrderItems().stream()
	                .map(this::convertOrderItemToDTO)
	                .collect(Collectors.toList());
	        dto.setOrderItems(orderItemDTOs);
	    }

	    if (order.getPayments() != null) {
	        List<PaymentDTO> paymentDTOs = order.getPayments().stream()
	                .map(this::convertPaymentToDTO)
	                .collect(Collectors.toList());
	        dto.setPayments(paymentDTOs);
	    }

	    if (order.getCustomer() != null) {
	        dto.setCustomer(convertCustomerToDTO(order.getCustomer()));
	    }

	    return dto;
	}
	
	private PaymentDTO convertPaymentToDTO(Payment payment) {
	    PaymentDTO paymentDTO = new PaymentDTO();
	    paymentDTO.setId(payment.getId());
	    paymentDTO.setPaymentDate(payment.getPaymentDate());
	    paymentDTO.setPaymentType(payment.getPaymentType());
	    paymentDTO.setAmount(payment.getAmount());
	    return paymentDTO;
	}
	
	private OrderItemDTO convertOrderItemToDTO(OrderItem orderItem) {
	    OrderItemDTO dto = new OrderItemDTO();
	    dto.setId(orderItem.getId());
	    dto.setProductName(orderItem.getProduct().getName()); 
	    dto.setQuantity(orderItem.getQuantity());
	    dto.setPrice(orderItem.getPrice()); 
	    return dto;
	}

	private CustomerDTO convertCustomerToDTO(Customer customer) {
	    CustomerDTO customerDTO = new CustomerDTO();
	    customerDTO.setId(customer.getId());
	    customerDTO.setName(customer.getName());
	    customerDTO.setMail(customer.getMail());
	    customerDTO.setPhone(customer.getPhone());
	    customerDTO.setIdentityNumber(customer.getIdentityNumber());
	    return customerDTO;
	}

}
