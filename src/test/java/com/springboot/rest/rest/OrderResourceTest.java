package com.springboot.rest.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;

import org.assertj.core.api.ZonedDateTimeAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.IntegrationTest;
import com.springboot.rest.domain.port.spi.OrderPersistencePort;
import com.springboot.rest.domain.service.OrderService;
import com.springboot.rest.infrastructure.entity.Order;
import com.springboot.rest.infrastructure.entity.OrderStatus;
import com.springboot.rest.infrastructure.entity.Product;
import com.springboot.rest.infrastructure.repository.OrderRepository;


@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class OrderResourceTest {

	private static final Long DEFAULT_ID = 1L;
	
	private static final Instant DEFAULT_ORDERDATE = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_ORDERDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
	
	private static final OrderStatus DEFAULT_ORDERSTATUS = OrderStatus.CREATED;
	private static final OrderStatus UPDATED_ORDERSTATUS = OrderStatus.DISPATCHED;
      
	static Set<Product> productlist = new HashSet<>();
    boolean p = productlist.add(new Product(1L,20000L,"GEANS"));
	private static final Set<Product> DEFAULT_LISTOFPRODUCTS =productlist;
	private static final Set<Product> UPDATED_LISTOFPRODUCTS = productlist ;
			
	
	private static final Instant DEFAULT_EXPECTEDDELIVERYDATE = Instant.ofEpochMilli(0L);
	private static final Instant UPDATED_EXPECTEDDELIVERYDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);
	
	private static final String DEFAULT_PAYMENTMETHOD = "CASH";
	private static final String UPDATED_PAYMENTMETHOD = "CASH";
	
	private static final Long DEFAULT_TOTALAMOUNT = 2000l;
	private static final Long UPDATED_TOTALAMOUNT = 2000l;
	
	private static final Long DEFAULT_CUSTOMERID = 0l;
	private static final Long UPDATED_CUSTOMERID = 0l;

	private static final String ENTITY_API_URL = "/api/order";
	private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
	private static final String ENTITY_API_URL_EXP_ID = ENTITY_API_URL +"/updateExpeDeliveryDate/{id}";
	private static Random random = new Random();
	private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
	
	@MockBean
	@Autowired
	private OrderRepository orderRepository;
	
	
	@MockBean
	@Autowired
	private OrderPersistencePort orderPersistencePort;
	
	@MockBean
	private OrderRepository orderRepoMock;
	@MockBean
	@Autowired
	private OrderService orderService;
	
	@MockBean
	@Autowired
	private EntityManager em;
	
	@Autowired
	private MockMvc restAMockMvc;
	
	private Order order;
	
	public static Order createEntity(EntityManager em) {
		Order order = new Order().id(DEFAULT_ID).orderDate(DEFAULT_ORDERDATE).orderStatus(DEFAULT_ORDERSTATUS).listOfProducts(DEFAULT_LISTOFPRODUCTS).expectedDeliveryDate(DEFAULT_EXPECTEDDELIVERYDATE)
				.paymentMethod(DEFAULT_PAYMENTMETHOD).totalAmount(DEFAULT_TOTALAMOUNT).customerID(DEFAULT_CUSTOMERID);
		return order;
		}
	public static Order createUpdatedEntity(EntityManager em) {
		Order order = new Order().orderDate(UPDATED_ORDERDATE).orderStatus(UPDATED_ORDERSTATUS).expectedDeliveryDate(UPDATED_EXPECTEDDELIVERYDATE)
				.paymentMethod(UPDATED_PAYMENTMETHOD).totalAmount(UPDATED_TOTALAMOUNT).customerID(UPDATED_CUSTOMERID);
		return order;
	}
	
	@BeforeEach
	public void initTest() {
		order = createEntity(em);
	}
	@Test
	@Transactional
	void createOrder() throws Exception {
		int databaseSizeBeforeCreate = orderRepository.findAll().size();
		
		restAMockMvc.perform(MockMvcRequestBuilders.post(ENTITY_API_URL)
				.contentType(MediaType.APPLICATION_PROBLEM_JSON)
				.content(TestUtil.convertObjectToJsonBytes(order)))
		.andExpect(status().isCreated());
		
		List<Order> orderList = orderRepository.findAll();
		System.out.println("order List  aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+orderList);
		assertThat(orderList.size()).isEqualTo(databaseSizeBeforeCreate + 1);
	
		Order testOrder = orderList.get(orderList.size()-1);
		assertThat(testOrder.getOrderDate()).isEqualTo(DEFAULT_ORDERDATE);
		assertThat(testOrder.getOrderStatus()).isEqualTo(DEFAULT_ORDERSTATUS);
		assertThat(testOrder.getListOfProducts()).isEqualTo(DEFAULT_LISTOFPRODUCTS);
		assertThat(testOrder.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTEDDELIVERYDATE);
		assertThat(testOrder.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENTMETHOD);
		assertThat(testOrder.getTotalAmount()).isEqualTo(DEFAULT_TOTALAMOUNT);
		assertThat(testOrder.getCustomerID()).isEqualTo(DEFAULT_CUSTOMERID);
		
		
	}
	@Test
	@Transactional
	void createOrderWithExistingId() throws Exception {
		order.setId(1L);
		
		int databaseSizeBeforeCreate = orderRepository.findAll().size();
		
		restAMockMvc
			.perform(MockMvcRequestBuilders.post(ENTITY_API_URL)
					.contentType(MediaType.APPLICATION_PROBLEM_JSON)
					.content(TestUtil.convertObjectToJsonBytes(order)))
			.andExpect(status().isBadRequest());
		
		List<Order> orderList = orderRepository.findAll();
		assertThat(orderList.size()).isEqualTo(databaseSizeBeforeCreate);
		
	}
	
	@Test
	@Transactional
	void putNonExistingOrder() throws Exception {
		int databaseSizeBeforeUpdate = orderRepository.findAll().size();
        order.setId(count.incrementAndGet());
        
        restAMockMvc
        	.perform(put(ENTITY_API_URL_ID, order.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(order)))
        	.andExpect(status().isBadRequest());
        
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList.size()).isEqualTo(databaseSizeBeforeUpdate);
	}
	
	@Test
	@Transactional
	void getAllOrders() throws Exception {
		orderRepository.saveAndFlush(order);
		
		restAMockMvc.perform(get(ENTITY_API_URL + "?sort=id,desc"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(jsonPath("$.[*].id").value(hasItems(order.getId().intValue())))
		.andExpect(MockMvcResultMatchers.jsonPath("$[*].orderDate").value(hasItems(DEFAULT_ORDERDATE.toString())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].orderStatus").value(hasItems(DEFAULT_ORDERSTATUS.toString())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].expectedDeliveryDate").value(hasItems(DEFAULT_EXPECTEDDELIVERYDATE.toString())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].paymentMethod").value(hasItems(DEFAULT_PAYMENTMETHOD)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].customerId").value(hasItems(DEFAULT_CUSTOMERID)));
		
	}
	
	@Test
	@Transactional
	void getOrder() throws Exception {
		orderRepository.saveAndFlush(order);
		
		
		restAMockMvc.perform(get(ENTITY_API_URL_ID, order.getId()))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value(order.getId().intValue()))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].orderDate").value(DEFAULT_ORDERDATE.toString()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$[*].orderStatus").value(DEFAULT_ORDERSTATUS.toString()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$[*].expectedDeliveryDate").value(DEFAULT_EXPECTEDDELIVERYDATE.toString()))
	        .andExpect(MockMvcResultMatchers.jsonPath("$[*].paymentMethod").value(DEFAULT_PAYMENTMETHOD))
	        .andExpect(MockMvcResultMatchers.jsonPath("$[*].customerId").value(DEFAULT_CUSTOMERID));
	}
	
	@Test
	@Transactional
	void deleteOrder() throws Exception{
		orderRepository.saveAndFlush(order);
		int databaseSizeBeforeDelete = orderRepository.findAll().size();
		
		restAMockMvc.perform(delete(ENTITY_API_URL_ID, order.getId()).accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isNoContent());
		
		List<Order> orderList = orderRepository.findAll();
		assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
	}
	
	@Test
	@Transactional
	void updateOrder() throws Exception{
		orderRepository.saveAndFlush(order);
		int databaseSizeBeforeUpdate = orderRepository.findAll().size();
		Order updateOrder = orderRepository.findById(order.getId()).get();
		em.detach(updateOrder);
		updateOrder.orderDate(UPDATED_ORDERDATE).orderStatus(UPDATED_ORDERSTATUS).listOfProducts(DEFAULT_LISTOFPRODUCTS).expectedDeliveryDate(UPDATED_EXPECTEDDELIVERYDATE)
		.paymentMethod(UPDATED_PAYMENTMETHOD).totalAmount(UPDATED_TOTALAMOUNT).customerID(UPDATED_CUSTOMERID);
		
		restAMockMvc
		.perform(put(ENTITY_API_URL_ID, updateOrder.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(TestUtil.convertObjectToJsonBytes(updateOrder))).andExpect(status().isOk());
		
		List<Order> orderlist = orderRepository.findAll();
		assertThat(orderlist).hasSize(databaseSizeBeforeUpdate);
		Order testOrder = orderlist.get(orderlist.size()-1);
		assertThat(testOrder.getOrderDate()).isEqualTo(UPDATED_ORDERDATE);
		assertThat(testOrder.getOrderStatus()).isEqualTo(UPDATED_ORDERSTATUS);
		assertThat(testOrder.getListOfProducts()).isEqualTo(DEFAULT_LISTOFPRODUCTS);
		assertThat(testOrder.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTEDDELIVERYDATE);
		assertThat(testOrder.getPaymentMethod()).isEqualTo(UPDATED_PAYMENTMETHOD);
		assertThat(testOrder.getTotalAmount()).isEqualTo(UPDATED_TOTALAMOUNT);
		assertThat(testOrder.getCustomerID()).isEqualTo(UPDATED_CUSTOMERID);
		
	}
	
	@Test
	@Transactional
	void partialUpdateOrder() throws Exception {
		orderRepository.saveAndFlush(order);
		int databaseSizeBeforeUpdate = orderRepository.findAll().size();
		Order partialUpdateOrder = new Order();
		partialUpdateOrder.setId(order.getId());
		partialUpdateOrder.orderStatus(UPDATED_ORDERSTATUS);
		restAMockMvc
		.perform(patch(ENTITY_API_URL_ID, partialUpdateOrder.getId())
				.content("application/problem+json")
				.content(TestUtil.convertObjectToJsonBytes(partialUpdateOrder))).andExpect(status().isOk());
		
		List<Order> orderlist =  orderRepository.findAll();
		assertThat(orderlist).hasSize(databaseSizeBeforeUpdate);
		Order testOrder = orderlist.get(orderlist.size() - 1);
		assertThat(testOrder.getOrderStatus()).isEqualTo(UPDATED_ORDERSTATUS);
	}
	
	@Test
	@Transactional
	void partialUpdateExpDateOrder() throws Exception {
		orderRepository.saveAndFlush(order);
		int databaseSizeBeforeUpdate = orderRepository.findAll().size();
		Order partialUpdateOrder = new Order();
		partialUpdateOrder.setId(order.getId());
		partialUpdateOrder.expectedDeliveryDate(UPDATED_EXPECTEDDELIVERYDATE);
		restAMockMvc
		.perform(patch(ENTITY_API_URL_EXP_ID, partialUpdateOrder.getId())
				.contentType(MediaType.APPLICATION_PROBLEM_JSON)
				.content(TestUtil.convertObjectToJsonBytes(partialUpdateOrder))).andExpect(status().isOk());
		
		List<Order> orderlist =  orderRepository.findAll();
		assertThat(orderlist).hasSize(databaseSizeBeforeUpdate);
		Order testOrder = orderlist.get(orderlist.size() - 1);
		assertThat(testOrder.getOrderStatus()).isEqualTo(UPDATED_EXPECTEDDELIVERYDATE);
	}
}