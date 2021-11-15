package com.springboot.rest.domain.port.api;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.springboot.rest.domain.dto.ProductDTO;
import com.springboot.rest.infrastructure.entity.Product;

public interface ProductServicePort {

	
	Product save(ProductDTO productDto);
	
	Optional<Product> findById(Long id);
}
