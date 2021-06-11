package com.delivery.api.delivery.dto.customer.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CustomerRequestDTO {
	
	@NotNull(message = "O nome do cliente é obrigatório")
	@Size(message = "O nome do cliente deve ter entre 2 e 50 caracteres", min = 2, max = 90)
	private String nameClient;
	
	@NotNull(message = "O celular do cliente é obrigatório")
	@Size(message = "O celular deve ter entre 9 e 15 caracteres", min = 9, max = 15)
	private String celPhone;
	
	@NotNull(message = "O endereco do cliente é obrigatório")
	@Size(message = "O endereco deve ter entre 10 e 255 caracteres", min = 10, max = 255)
	private String address;

}
