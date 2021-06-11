package com.delivery.api.delivery.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

	private String nameClient;
	
	private String celPhone;
	
	private String address;
	
}
