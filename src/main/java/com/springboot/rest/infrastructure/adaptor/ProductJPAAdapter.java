package com.springboot.rest.infrastructure.adaptor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.ProductDTO;
import com.springboot.rest.domain.port.spi.ProductPersistencePort;
import com.springboot.rest.infrastructure.entity.Product;
import com.springboot.rest.infrastructure.repository.ProductRepository;

@Service
@Transactional
public class ProductJPAAdapter implements ProductPersistencePort{

	@Autowired
	private final ProductRepository productRepository;
	
	
	
	public ProductJPAAdapter(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}



	@Override
	public Optional<Product> findById(Long id) {
		return productRepository.findById(id);
	}



	@Override
	public Product save(ProductDTO productDto) {
		//Product product = 
		//return productRepository.save(productDto);
	return null;
	}

}
