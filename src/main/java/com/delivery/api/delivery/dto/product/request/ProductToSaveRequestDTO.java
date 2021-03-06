package com.delivery.api.delivery.dto.product.request;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductToSaveRequestDTO {
	
	@NotNull(message = "O nome é obrigatório")
	@Size(message = "O nome deve ter entre 2 e 50 caracteres", min = 2, max = 90)
	private String name;
	
	@NotNull(message = "O preco é obrigatório")
	private BigDecimal price;	
	
	private BigDecimal priceDebit;
	
	private BigDecimal priceCredit;	
	
	@NotNull(message = "A descricao é obrigatória")
	@Size(message = "A descricao deve ter entre 4 e 255 caracteres", min = 4, max = 255)
	private String description;
	
	private Boolean available;
	
	ProductToSaveRequestDTO() {
		this.priceDebit = BigDecimal.ZERO;
		this.priceCredit = BigDecimal.ZERO;
		this.available = Boolean.FALSE;
	}

}