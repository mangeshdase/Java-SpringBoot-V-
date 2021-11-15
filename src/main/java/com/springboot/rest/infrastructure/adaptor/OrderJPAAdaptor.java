package com.springboot.rest.infrastructure.adaptor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.OrderDTO;
import com.springboot.rest.domain.port.spi.OrderPersistencePort;
import com.springboot.rest.infrastructure.entity.Order;
import com.springboot.rest.infrastructure.entity.OrderStatus;
import com.springboot.rest.infrastructure.repository.OrderRepository;
import com.springboot.rest.mapper.OrderMapper;

@Service
@Transactional
public class OrderJPAAdaptor implements OrderPersistencePort{

	@Autowired
	private final OrderRepository orderRepository;
	private final OrderMapper orderMapper;
	
	
	public OrderJPAAdaptor(OrderRepository orderRepository, OrderMapper orderMapper) {
		this.orderRepository = orderRepository;
		this.orderMapper = orderMapper;
	}

	@Override
	public List<Order> finadAll() {
		return orderRepository.findAll();
	}

	@Override
	public Optional<Order> findById(Long id) {
		return orderRepository.findById(id);
	}

	@Override
	public Order save(OrderDTO orderDto) {
		Order order = orderMapper.dtoToEntity(orderDto);
		return orderRepository.save(order);
	}

	@Override
	public boolean existsById(Long id) {
		return orderRepository.existsById(id);
	}

	@Override
	public void deleteById(Long id) {
		orderRepository.deleteById(id);
		
	}

	

	@Override
	public List<Order> findAllOrderByTotalAmountAsc() {
		return orderRepository.findAllOrderByTotalAmountAsc();
	}

	@Override
	public List<Order> findAllOrderByExpectedDeliveryDateAsc() {
		return orderRepository.findAllOrderByExpectedDeliveryDateAsc();
	}

	@Override
	public List<Order> findByOrderStatus(OrderStatus orderStatus) {
		// TODO Auto-generated method stub
		return orderRepository.findByOrderStatus(orderStatus);
	}

	

}
