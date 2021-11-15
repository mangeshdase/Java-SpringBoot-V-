package com.springboot.rest.domain.port.spi;

import java.util.Optional;
import java.util.Set;

import com.springboot.rest.domain.dto.ProductDTO;
import com.springboot.rest.infrastructure.entity.Product;

public interface ProductPersistencePort {

	Product save(ProductDTO productDto);
	
	Optional<Product> findById(Long id);
}
