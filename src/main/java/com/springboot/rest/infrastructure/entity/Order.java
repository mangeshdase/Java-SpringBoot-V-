package com.springboot.rest.infrastructure.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;
@Entity
@Data
@Table(name = "order1")
public class Order implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long id;
	
	@Column(name = "order_date")
	private Instant orderDate;
	
	//@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private  OrderStatus orderStatus;

	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
	@JoinTable(name = "list_of_products",
	joinColumns = { @JoinColumn(name ="order_id", referencedColumnName = "order_id", updatable = false)},
	inverseJoinColumns = { @JoinColumn(name = "prod_id", referencedColumnName = "prod_id")})
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Product> listOfProducts;
	
	//private String listOfProducts;
	
	 
	 @Column(name = "expe_delivery_date")
	 private Instant expectedDeliveryDate;
	 
	 @Column(name = "payment_method")
	 private String paymentMethod;
	 
	 @Column(name = "total_amount")
	 private Long totalAmount;
	 
	 @Column(name = "customer_id")
	 private Long customerID;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Order)) {
			return false;
		}
		Order other = (Order) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Order id(Long id) {
		this.id = id;
		return this;
	}
	
	public Order orderDate(Instant orderDate) {
		this.orderDate = orderDate;
		return this;
	}

	public Order orderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
		return this;
	}

	public Order listOfProducts(Set<Product> Listofproducts) {
		this.listOfProducts = Listofproducts;
		return this;
	}

	public Order expectedDeliveryDate(Instant Expecteddeliverydate) {
		this.expectedDeliveryDate = Expecteddeliverydate;
		return this;
	}

	public Order paymentMethod(String Paymentmethod) {
		this.paymentMethod = Paymentmethod;
		return this;
	}

	public Order totalAmount(Long Totalamount) {
		this.totalAmount = Totalamount;
		return this;
	}

	public Order customerID(Long defaultCustomerid) {
		this.customerID = defaultCustomerid;
		return this;
	}
	
	
	
}
