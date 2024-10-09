package com.ecommerce.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import com.ecommerce.api.dtos.OrderDTO;
import com.ecommerce.api.services.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	private final PagedResourcesAssembler<OrderDTO> pagedResourcesAssembler;
	

    public OrderController(OrderService orderService, PagedResourcesAssembler<OrderDTO> pagedResourcesAssembler) {
        this.orderService = orderService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }
    
    @GetMapping("/all")
    public ResponseEntity<PagedModel<EntityModel<OrderDTO>>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDTO> pagedResult = orderService.getAllOrders(pageable);
        PagedModel<EntityModel<OrderDTO>> pagedModel = pagedResourcesAssembler.toModel(pagedResult);

        if (pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pagedModel);
        }
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
    	OrderDTO orderDTO = orderService.getOrderById(id);
        if (orderDTO != null) {
            return ResponseEntity.ok(orderDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    @PostMapping("/search/dates")
    public ResponseEntity<PagedModel<EntityModel<OrderDTO>>> findByDateRange(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<OrderDTO> pagedResult = orderService.getOrdersByDateRange(startDate, endDate, pageable);
        PagedModel<EntityModel<OrderDTO>> pagedModel = pagedResourcesAssembler.toModel(pagedResult);

        if (pagedResult.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(pagedModel);
        }
    }

    @PostMapping("/search/customer")
    public ResponseEntity<List<OrderDTO>> findByCustomerIdentityNumber(
            @RequestParam String identityNumber) {

        List<OrderDTO> orders = orderService.getOrdersByCustomerIdentityNumber(identityNumber);

        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(orders);
        }
    }
}
