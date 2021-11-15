package com.springboot.rest.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.rest.infrastructure.entity.Order;
import com.springboot.rest.infrastructure.entity.OrderStatus;
import com.springboot.rest.infrastructure.entity.Product;

public interface OrderRepository extends JpaRepository<Order, Long>{

	List<Order> findByOrderStatus(OrderStatus orderStatus);
	
	@Query(value ="select * from order1 order by total_amount", nativeQuery = true)
	List<Order> findAllOrderByTotalAmountAsc();
	
	@Query(value = "select * from order1 order by expe_delivery_date", nativeQuery = true)
	List<Order> findAllOrderByExpectedDeliveryDateAsc();
	
//	@Query(value= "select p.prod_id, p.prod_name,p.prod_value from products as p inner join order1_list_of_products as \r\n"
//			+ "pl on p.prod_id = pl.list_of_products_prod_id where pl.order_order_id =:id",nativeQuery = true)
//	Optional<Order> findById(@Param("id") Long id);
}
