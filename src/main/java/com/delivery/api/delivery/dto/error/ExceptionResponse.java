package com.delivery.api.delivery.dto.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExceptionResponse {
	
	private String error;

}
