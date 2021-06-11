package com.delivery.api.delivery.converter.request;

import java.util.List;

import com.delivery.api.delivery.dto.customer.response.CustomerResponseDTO;
import com.delivery.api.delivery.dto.requestEntity.request.RequestDTO;
import com.delivery.api.delivery.dto.requestEntity.response.RequestResponseDTO;
import com.delivery.api.delivery.model.Customer;
import com.delivery.api.delivery.model.Product;
import com.delivery.api.delivery.model.Request;

public class Converter {
	
	public static Request toRequest(RequestDTO request, Customer customer, List<Product> productsRequest) {
		return Request.builder()
				.customer(customer)
				.methodPayment(request.getMethodPayment())
				.observation(request.getObservation())
				.value(request.getValue())
				.isOpen(request.getIsOpen())
				.products(productsRequest)
				.build();
		
	}
	
	public static RequestResponseDTO toRequestResponseDTO(Request request, CustomerResponseDTO customer, List<Long> productsRequest) {
		return RequestResponseDTO.builder()
				.id(request.getId())
				.customer(customer)
				.products(productsRequest)
				.value(request.getValue())
				.observation(request.getObservation())
				.methodPayment(request.getMethodPayment())
				.isOpen(request.getIsOpen())
				.build();
		
	}

}