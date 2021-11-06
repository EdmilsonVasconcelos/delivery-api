package com.delivery.api.delivery.dto.product.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReponseDTO {
	
	private Long id;
	
	private String name;

	private String description;

	private BigDecimal price;

	private BigDecimal priceCredit;

	private BigDecimal priceDebit;

}
