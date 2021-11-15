package com.springboot.rest.domain.dto;

import com.springboot.rest.infrastructure.entity.OrderStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class OrderStatusDTO {

	private Long id;
	
	private OrderStatus orderStatus;

	public OrderStatusDTO(Long id, OrderStatus orderStatus) {
		this.id = id;
		this.orderStatus = orderStatus;
	}
	
	
}
