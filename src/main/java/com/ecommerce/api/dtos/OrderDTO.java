package com.ecommerce.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private LocalDateTime orderDate;
    private String orderStatus;  
    private BigDecimal totalAmount;  
    private List<OrderItemDTO> orderItems;  
    private CustomerDTO customer; 
    private List<PaymentDTO> payments;
}
