package com.springboot.rest.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.rest.domain.dto.OrderDTO;
import com.springboot.rest.domain.port.api.OrderServicePort;
import com.springboot.rest.infrastructure.entity.Order;
import com.springboot.rest.infrastructure.entity.OrderStatus;
import com.springboot.rest.rest.errors.BadRequestAlertException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class OrderResource {

	private final Logger log = LoggerFactory.getLogger(OrderResource.class);
	
	private static final String ENTITY_NAME = "order1";
	
	@Value("${jhipster.clientApp.name}")
	private String applicationName;
	
	private final OrderServicePort orderServicePort;

	public OrderResource(OrderServicePort orderServicePort) {
		super();
		this.orderServicePort = orderServicePort;
	}
	
	@PostMapping("/order")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException{
		log.debug("REST request to save Order :{}" , orderDTO);
//		if(orderDTO.getId() != null) {
//			throw new BadRequestAlertException("Order new cannot already have an ID", ENTITY_NAME, "idexists");
//		}
		Order order = orderServicePort.save(orderDTO);
		OrderDTO orderDtoRespose = new OrderDTO(order);
		return ResponseEntity
				.created(new URI("/api/order/" + order.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, orderDtoRespose.getId().toString()))
				.body(orderDtoRespose);
		
	}
	@PutMapping("/order/{id}")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<OrderDTO> updateOrder(@PathVariable(value = "id", required = false) final Long id, @RequestBody OrderDTO orderDTO) throws URISyntaxException{
		log.debug("REST request to update Order : {},{}", id, orderDTO);
		Order order = orderServicePort.update(id, orderDTO);
		OrderDTO orderDtoRespose = new OrderDTO(order);
		return ResponseEntity
				.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDtoRespose.getId().toString()))
				.body(orderDtoRespose);
	}
//	
//	@PutMapping("/order/updateOrderStatus/{id}")
//	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
//	public ResponseEntity<OrderStatusDTO> updateOrderStatus(@PathVariable(value = "id", required = false) final Long id, @RequestBody OrderStatusDTO orderDTO) throws URISyntaxException{
//		log.debug("REST request to update Order Status : {}, {}", id, orderDTO);
//		Order order = orderServicePort.update(id, orderDTO);
//		OrderDTO orderDtoResponse = new OrderDTO(order);
//		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDtoResponse.getId().toString()))
//				.body(orderDtoResponse);
//	}
	
	@GetMapping("/order")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public List<OrderDTO> getAllOrders(){
		log.debug("REST request to get All Orders");
		List<OrderDTO> orderDTOs = orderServicePort.findAll().stream().map(order -> new OrderDTO(order)).collect(Collectors.toList());
		return orderDTOs;
	}
	
	@GetMapping("/order/{id}")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Order> getOrder(@PathVariable Long id){
		log.debug("REST request to get Order : {}", id);
		Optional<Order> order = orderServicePort.findById(id);
		return ResponseUtil.wrapOrNotFound(order);
	}
	
	@DeleteMapping("/order/{id}")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
		log.debug("REST request to delete order : {}",id);
		orderServicePort.deleteById(id);
		return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
	}
	
	@GetMapping("/order/byStatus/{orderstatus}")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public List<Order> getOrderByStatus(@PathVariable OrderStatus orderstatus){
		log.debug("REST request to get order by order status : {}", orderstatus);
		List<Order> order = orderServicePort.findByOrderStatus(orderstatus);
		return order;
	}
	
	@GetMapping("order/sortedbyTotalAmount")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public List<Order> getOrdersByTotalAmountAsc(){
		log.debug("REST request to get orders in asc by totalAmount ");
		List<Order> order =  orderServicePort.findAllOrderByTotalAmountAsc();
		return order;
	}
	
	
	@GetMapping("order/sortedByExpeDeliveryDate")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public List<Order> getOrdersByExpeDeliveryDate(){
		log.debug("REST request to get orders in asc by expe_delivery_date");
		List<Order> order = orderServicePort.findAllOrderByExpectedDeliveryDateAsc();
		return order;
	}
	
	@PatchMapping(value = "/order/{id}", consumes = "application/merge-patch+json")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Order> updateOrderStatus(@PathVariable(value = "id", required = false) final Long id, @RequestBody OrderDTO orderDto) throws URISyntaxException{
		log.debug("REST request to update orderStatus:{},{}", id, orderDto);
		Optional<Order> result = orderServicePort.updateOrderStatus(id, orderDto);
		return ResponseUtil.wrapOrNotFound(result , HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDto.getId().toString()));
	}
	
	@PatchMapping(value = "/order/updateExpeDeliveryDate/{id}", consumes = "application/merge-patch+json")
	@Operation(summary = "/order", security = @SecurityRequirement(name = "bearerAuth"))
	public ResponseEntity<Order> updateExpeDeliveryDate(@PathVariable(value = "id", required = false) final Long id, @RequestBody OrderDTO orderDto) throws URISyntaxException{
		log.debug("REST request to update expectedDeliveryDate :{}, {}",id, orderDto);
		Optional<Order> result = orderServicePort.updateExpeDeliveryDate(id, orderDto);
		return ResponseUtil.wrapOrNotFound(result, HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, orderDto.getId().toString()));
	}
	
}
