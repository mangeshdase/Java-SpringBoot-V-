package com.springboot.rest.domain.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class ProductDTO {
	
	
	private Long id;
	
	
	private Long price;
	
	private String name;

	public ProductDTO(Long id, Long price, String name) {
		super();
		this.id = id;
		this.price = price;
		this.name = name;
	}
	
	
	

}
