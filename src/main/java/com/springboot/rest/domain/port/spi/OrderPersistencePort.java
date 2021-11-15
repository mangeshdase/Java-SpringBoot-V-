package com.springboot.rest.domain.port.spi;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.springboot.rest.domain.dto.OrderDTO;
import com.springboot.rest.infrastructure.entity.Order;
import com.springboot.rest.infrastructure.entity.OrderStatus;

public interface OrderPersistencePort {

	List<Order> finadAll();
	
	Optional<Order> findById(Long id);
	
	Order save(OrderDTO orderDto);
	
	boolean existsById(Long id);
	
	void deleteById(Long id);
	
	List<Order> findByOrderStatus(OrderStatus orderStatus);
	
	List<Order> findAllOrderByTotalAmountAsc();
	
	List<Order> findAllOrderByExpectedDeliveryDateAsc();
	
	//Optional<Order> findById(@Param("id") Long id);

}
