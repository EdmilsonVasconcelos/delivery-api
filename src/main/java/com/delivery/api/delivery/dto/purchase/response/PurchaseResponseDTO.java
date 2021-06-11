package com.delivery.api.delivery.dto.purchase.response;

import java.math.BigDecimal;
import java.util.List;

import com.delivery.api.delivery.dto.customer.response.CustomerResponseDTO;
import com.delivery.api.delivery.dto.product.response.ProductReponseDTO;
import com.delivery.api.delivery.enums.MethodPayment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseResponseDTO {
	
	private Long id;
	
	private CustomerResponseDTO customer;
	
	private List<ProductReponseDTO> products;
	
	private String observation;
	
	private MethodPayment methodPayment;
	
	private BigDecimal value;
	
	private Boolean isOpen;

}
