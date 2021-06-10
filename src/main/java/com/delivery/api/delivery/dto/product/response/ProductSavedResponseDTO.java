package com.delivery.api.delivery.dto.product.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSavedResponseDTO {
	
	private Long id;
	
	private String name;
	
	private BigDecimal price;	
	
	private BigDecimal priceDebit;
	
	private BigDecimal priceCredit;	
	
	private String description;
	
	private Boolean available;

}
