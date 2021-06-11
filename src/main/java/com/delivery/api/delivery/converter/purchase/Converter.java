package com.delivery.api.delivery.converter.purchase;

import java.util.List;

import com.delivery.api.delivery.dto.customer.response.CustomerResponseDTO;
import com.delivery.api.delivery.dto.purchase.request.PurchaseRequestDTO;
import com.delivery.api.delivery.dto.purchase.response.PurchaseResponseDTO;
import com.delivery.api.delivery.model.Customer;
import com.delivery.api.delivery.model.Product;
import com.delivery.api.delivery.model.Purchase;

public class Converter {
	
	public static Purchase toRequest(PurchaseRequestDTO request, Customer customer, List<Product> productsRequest) {
		return Purchase.builder()
				.customer(customer)
				.methodPayment(request.getMethodPayment())
				.observation(request.getObservation())
				.value(request.getValue())
				.isOpen(request.getIsOpen())
				.products(productsRequest)
				.build();
		
	}
	
	public static PurchaseResponseDTO toRequestResponseDTO(Purchase request, CustomerResponseDTO customer, List<Long> productsRequest) {
		return PurchaseResponseDTO.builder()
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