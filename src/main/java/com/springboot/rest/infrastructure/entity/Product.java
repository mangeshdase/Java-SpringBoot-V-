package com.springboot.rest.infrastructure.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@Entity
@Data
@Table(name = "products")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@NoArgsConstructor
public class Product {
	
	@Id
	@Column(name = "prod_id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Prod_value")
	private Long price;
	
	@Column(name = "Prod_name")
	private String name;

	

	
//	@ManyToMany(mappedBy = "products", fetch = FetchType.LAZY)
//	private Set<Order> orders = new HashSet<>();
}
