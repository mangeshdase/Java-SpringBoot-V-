package com.springboot.rest.domain.service;



import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.OrderDTO;
import com.springboot.rest.domain.dto.OrderExpeDateDTO;
import com.springboot.rest.domain.dto.OrderStatusDTO;
import com.springboot.rest.domain.port.api.OrderServicePort;
import com.springboot.rest.domain.port.spi.OrderPersistencePort;
import com.springboot.rest.domain.port.spi.ProductPersistencePort;
import com.springboot.rest.infrastructure.entity.Order;
import com.springboot.rest.infrastructure.entity.OrderStatus;
import com.springboot.rest.infrastructure.entity.Product;
import com.springboot.rest.infrastructure.repository.ProductRepository;
import com.springboot.rest.mapper.OrderMapper;
import com.springboot.rest.rest.errors.BadRequestAlertException;
@Service
@Transactional
public class OrderService implements OrderServicePort {

	private static final String ENTITY_NAME = "order1";
	
	private final OrderPersistencePort orderPersistancePort;
	
	private final ProductPersistencePort productPersistencePort;
	private final OrderMapper orderMapper;
	
	
	
	

	

	public OrderService(OrderPersistencePort orderPersistancePort, OrderMapper orderMapper, ProductPersistencePort productPersistencePort) {
		this.orderPersistancePort = orderPersistancePort;
		this.productPersistencePort = productPersistencePort;
		this.orderMapper = orderMapper;
	}

	@Override
	public Order save(OrderDTO orderDto) {
		Set<Product> oid = orderDto.getListOfProducts();
		Long amount = 0l;
		for(Product p : oid) {
			Long pid = p.getId();
		Optional<Product> order =  productPersistencePort.findById(pid);
		Product prod = order.get();
		 amount = amount + prod.getPrice();
		}
			orderDto.setTotalAmount(amount);
		return orderPersistancePort.save(orderDto);
	}

	@Override
	public Order update(Long id, OrderDTO orderDto) {
		if(orderDto.getId() == null) {
			throw new BadRequestAlertException("invalid id", ENTITY_NAME, "id null");
		}
		if(!Objects.equals(id, orderDto.getId())) {
			throw new BadRequestAlertException("Invalid ID", ENTITY_NAME,"Idinvalid");
		}
		if(!orderPersistancePort.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
		return orderPersistancePort.save(orderDto);
	}

	@Override
	public boolean existsById(Long id) {
		return orderPersistancePort.existsById(id);
	}

	@Override
	public List<Order> findAll() {
		return orderPersistancePort.finadAll();
	}

	@Override
	public Optional<Order> findById(Long id) {
		return orderPersistancePort.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		orderPersistancePort.deleteById(id);
		
	}

	
	@Override
	public Optional<Order> updateExpeDeliveryDate(Long id, OrderDTO orderDTO) {
		if(orderDTO.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if(!Objects.equals(id, orderDTO.getId())) {
			throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "idinvalid");
		}
		if(!orderPersistancePort.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
		return orderPersistancePort.findById(orderDTO.getId())
				.map(existingOrder -> {
					if(orderDTO.getExpectedDeliveryDate() != null) {
						existingOrder.setExpectedDeliveryDate(orderDTO.getExpectedDeliveryDate());
						}
					return existingOrder;
					}
				).map(updatedOrder -> {
					OrderDTO updatedOrderDTO = orderMapper.entityToDto(updatedOrder);
					orderPersistancePort.save(updatedOrderDTO);
					return updatedOrder;
					});
		}
	@Override
	public Optional<Order> updateOrderStatus(Long id, OrderDTO orderDto) {
		if(orderDto.getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		if(!Objects.equals(id, orderDto.getId())) {
			throw new BadRequestAlertException("Invalid Id", ENTITY_NAME, "idinvalid");
		}
		if(!orderPersistancePort.existsById(id)) {
			throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
		}
		return orderPersistancePort
				.findById(orderDto.getId())
				.map(
						exitingOrder -> {
							
							if(orderDto.getOrderStatus() != null) {
								exitingOrder.setOrderStatus(orderDto.getOrderStatus());
							}
							return exitingOrder;	
						}
						).map(updatedOrder -> {
							OrderDTO updatedOrderDTO = orderMapper.entityToDto(updatedOrder);
							orderPersistancePort.save(updatedOrderDTO);
							return updatedOrder;
						});
		
	}


	@Override
	public List<Order> findByOrderStatus(OrderStatus orderStatus) {
		return orderPersistancePort.findByOrderStatus(orderStatus);
	}

	@Override
	public List<Order> findAllOrderByTotalAmountAsc() {
		return orderPersistancePort.findAllOrderByTotalAmountAsc();
	}

	@Override
	public List<Order> findAllOrderByExpectedDeliveryDateAsc() {
		return orderPersistancePort.findAllOrderByExpectedDeliveryDateAsc();
	}


	

	

	

	

	

	

}
