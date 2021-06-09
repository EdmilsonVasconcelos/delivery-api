package com.delivery.api.delivery.dto.auth.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
	
	private String type;
	
	private String token;

}