package com.delivery.api.delivery.dto.purchase.request;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.delivery.api.delivery.dto.address.AddressDTO;
import com.delivery.api.delivery.dto.customer.request.CustomerRequestDTO;
import com.delivery.api.delivery.enums.MethodPayment;

import com.delivery.api.delivery.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PurchaseRequestDTO {

	@NotNull(message = "O endereço é obrigatório")
	private AddressDTO address;

	@Size(min = 8, max = 15, message = "O campo telefone deve ter entre 8 e 15 caracteres")
	@NotNull(message = "O telefone é obrigatório")
	private String phoneNumber;
	
	@NotNull(message = "Os produto são obrigatórios")
	private List<Long> products;
	
	@NotNull(message = "A forma de pagamento é obrigatória")
	private MethodPayment methodPayment;
	
}
