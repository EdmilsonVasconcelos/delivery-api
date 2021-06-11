package com.delivery.api.delivery.dto.requestEntity.response;

import java.math.BigDecimal;
import java.util.List;

import com.delivery.api.delivery.dto.customer.response.CustomerResponseDTO;
import com.delivery.api.delivery.enums.MethodPayment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RequestResponseDTO {
	
	private Long id;
	
	private CustomerResponseDTO customer;
	
	private List<Long> products;
	
	private String observation;
	
	private MethodPayment methodPayment;
	
	private BigDecimal value;
	
	private Boolean isOpen;

}
