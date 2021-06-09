package com.delivery.api.delivery.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ErrorValidationDTO {
	
	private String field;
	
	private String error;

}
