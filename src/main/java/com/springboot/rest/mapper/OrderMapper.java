package com.springboot.rest.mapper;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.springboot.rest.domain.dto.OrderDTO;
import com.springboot.rest.infrastructure.entity.Authority;
import com.springboot.rest.infrastructure.entity.Order;

@Component
public class OrderMapper {

	private ModelMapper modelMapper = new ModelMapper();
	
	
	public OrderDTO entityToDto(Order order) {
		return modelMapper.map(order, OrderDTO.class);
	}
	
	public List<OrderDTO> entitiesToDTOs(List<Order> orders){
		return orders.stream().filter(Objects::nonNull).map(this::entityToDto).collect(Collectors.toList());
	}
	
	public Order dtoToEntity(OrderDTO orderDTO) {
		return modelMapper.map(orderDTO, Order.class);
	}
	
	public List<Order> dtosToEntities(List<OrderDTO> ordersDTOs){
		return ordersDTOs.stream().filter(Objects::nonNull).map(this::dtoToEntity).collect(Collectors.toList());
	}
	
	
	
	
	private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString){
	
		Set<Authority> authorities = new HashSet<>();
		
		if(authoritiesAsString != null) {
			authorities = authoritiesAsString.stream().map(
					string -> {
						Authority auth = new Authority();
					auth.setName(string);
					return auth;
					}
					).collect(Collectors.toSet());
		}
		return authorities;
	}
	
	public Order orderFromId(Long id) {
		if(id == null) {
			return null;
		}
		Order order = new Order();
		order.setId(id);
		return order;
	}
	
	
	@Named("id")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	public OrderDTO toDtoId(Order order) {
		if( order == null) {
			return null;
		}
		OrderDTO orderDTO = new OrderDTO();
		orderDTO.setId(order.getId());
		return orderDTO;
	}
	
	@Named("idset")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	public Set<OrderDTO> toDtoIdSet(Set<Order> orders){
		if(orders == null) {
			return Collections.emptySet();
		}
		Set<OrderDTO> orderSet = new HashSet<>();
		for (Order orderEntity : orders) {
			orderSet.add(this.toDtoId(orderEntity));
		}
		return orderSet;
	}
}
