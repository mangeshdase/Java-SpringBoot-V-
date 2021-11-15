package com.springboot.rest.domain.port.api;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.OrderDTO;
import com.springboot.rest.domain.dto.OrderExpeDateDTO;
import com.springboot.rest.domain.dto.OrderStatusDTO;
import com.springboot.rest.infrastructure.entity.Order;
import com.springboot.rest.infrastructure.entity.OrderStatus;

public interface OrderServicePort {

	Order save(OrderDTO orderDto);
	
	Order update(Long id, OrderDTO orderDto);
	
	boolean existsById(Long id);
	
	List<Order> findAll();
	
	Optional<Order> findById(Long id);
	
	void deleteById(Long id);
	
	Optional<Order> updateOrderStatus(Long id, OrderDTO orderDto);
	
	Optional<Order> updateExpeDeliveryDate(Long id, OrderDTO orderDTO);
	
	
	List<Order> findByOrderStatus(OrderStatus orderStatus);

	List<Order> findAllOrderByTotalAmountAsc();
	
	List<Order> findAllOrderByExpectedDeliveryDateAsc();
	
	
	

}
