package com.delivery.api.delivery.dto.requestEntity.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.delivery.api.delivery.dto.customer.request.CustomerRequestDTO;
import com.delivery.api.delivery.enums.MethodPayment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RequestDTO {
	
	private CustomerRequestDTO customer;
	
	@NotNull(message = "O id do produto é obrigatório")
	private List<Long> products;
	
	private String observation;
	
	@NotNull(message = "A forma de pagamento é obrigatória")
	private MethodPayment methodPayment;
	
	@NotNull(message = "A forma de pagamento é obrigatória")
	private BigDecimal value;
	
	private Boolean isOpen;
	
	RequestDTO() {
		this.isOpen = true;
	}

}
