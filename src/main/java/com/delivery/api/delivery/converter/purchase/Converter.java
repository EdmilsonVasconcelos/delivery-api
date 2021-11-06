package com.delivery.api.delivery.converter.purchase;

import java.util.List;

import com.delivery.api.delivery.dto.customer.response.CustomerResponseDTO;
import com.delivery.api.delivery.dto.product.response.ProductReponseDTO;
import com.delivery.api.delivery.dto.purchase.request.PurchaseRequestDTO;
import com.delivery.api.delivery.dto.purchase.response.PurchaseResponseDTO;
import com.delivery.api.delivery.model.Customer;
import com.delivery.api.delivery.model.Product;
import com.delivery.api.delivery.model.Purchase;

public class Converter {
	
	public static Purchase toPurachase(PurchaseRequestDTO request, Customer customer, List<Product> productsRequest) {
		return Purchase.builder()
				.customer(customer)
				.methodPayment(request.getMethodPayment())
				.build();
	}
	
	public static PurchaseResponseDTO toPurchaseResponseDTO(Purchase request, CustomerResponseDTO customer, List<ProductReponseDTO> products) {
		return PurchaseResponseDTO.builder()
				.id(request.getId())
				.customer(customer)
				.products(products)
				.value(request.getValue())
				.methodPayment(request.getMethodPayment())
				.build();
	}

}