package com.springboot.rest.domain.dto;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.springboot.rest.infrastructure.entity.Order;
import com.springboot.rest.infrastructure.entity.OrderStatus;
import com.springboot.rest.infrastructure.entity.Product;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@EqualsAndHashCode
public class OrderDTO {

	private Long id;
	
	private Instant orderDate;
	
	private OrderStatus orderStatus;
	
	private Set<Product> listOfProducts;
	
	private Instant expectedDeliveryDate;
	
	private String paymentMethod;
	
	private Long totalAmount;
	
	private Long customerId;
	
	public OrderDTO(Order order) {
		this.id=order.getId();
		this.orderDate = order.getOrderDate();
		this.orderStatus= order.getOrderStatus();
		this.expectedDeliveryDate = order.getExpectedDeliveryDate();
		this.listOfProducts = order.getListOfProducts();
		this.paymentMethod = order.getPaymentMethod();
		this.totalAmount = order.getTotalAmount();
		this.customerId = order.getCustomerID();
		}

	
}
