package com.springboot.rest.domain.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.ProductDTO;
import com.springboot.rest.domain.port.api.ProductServicePort;
import com.springboot.rest.domain.port.spi.ProductPersistencePort;
import com.springboot.rest.infrastructure.entity.Product;


@Service
@Transactional
public class ProductService implements ProductServicePort{

	private static final String ENTITY_NAME = "products";
	
	private final ProductPersistencePort productPersistancePort;
	
	
	
	public ProductService(ProductPersistencePort productPersistancePort) {
		super();
		this.productPersistancePort = productPersistancePort;
	}

	@Override
	public Product save(ProductDTO productDto) {
		// TODO Auto-generated method stub
		return productPersistancePort.save(productDto);
	}

	@Override
	public Optional<Product> findById(Long id) {
		// TODO Auto-generated method stub
		return productPersistancePort.findById(id);
	}

}
