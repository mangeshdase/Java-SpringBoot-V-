package com.springboot.rest.domain.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderExpeDateDTO {

	private Long id;
	
	private Date expectedDeliveryDate;
	
	
}
