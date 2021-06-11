package com.delivery.api.delivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.delivery.converter.purchase.Converter;
import com.delivery.api.delivery.dto.customer.response.CustomerResponseDTO;
import com.delivery.api.delivery.dto.purchase.request.PurchaseRequestDTO;
import com.delivery.api.delivery.dto.purchase.response.PurchaseResponseDTO;
import com.delivery.api.delivery.exception.ProductNotExistsException;
import com.delivery.api.delivery.model.Customer;
import com.delivery.api.delivery.model.Product;
import com.delivery.api.delivery.model.Purchase;
import com.delivery.api.delivery.repository.ProductRepository;
import com.delivery.api.delivery.repository.RequestRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PurchaseService {
	
	private static final String PRODUCT_WITH_ID_NOT_EXISTS = "Produto com id %s nao existe";
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public PurchaseResponseDTO savePurchase(PurchaseRequestDTO request) {
		
		log.debug("PurchaseService.savePurchase - Start - Request:  [{}]", request);
		
		Purchase requestToSave = Converter.toRequest(request, mapper.map(request.getCustomer(), Customer.class), getProductsPurchase(request.getProducts()));
		
		Purchase requestSaved = requestRepository.save(requestToSave);
		
		PurchaseResponseDTO response = Converter.toRequestResponseDTO(requestSaved, mapper.map(request.getCustomer(), CustomerResponseDTO.class), request.getProducts());
		
		log.debug("PurchaseService.savePurchase - Finish - Request [{}], Response:  [{}]", request, response);
		
		return response;
		
	}
	
	public List<PurchaseResponseDTO> getAllpurchases() {
		
		log.debug("PurchaseService.getAllpurchases - Start");
		
		List<Purchase> allRequests = requestRepository.findByIsOpenTrue();
		
		List<PurchaseResponseDTO> response = new ArrayList<>();
		
		allRequests.forEach(request -> {
			
			PurchaseResponseDTO requestResponseDTO = Converter.toRequestResponseDTO(request, mapper.map(request.getCustomer(), CustomerResponseDTO.class), getIdsByListProducts(request.getProducts()));
			
			response.add(requestResponseDTO);
			
		});
		
		log.debug("PurchaseService.getAllpurchases - Finish -  Response:  [{}]", response);
		
		return response;
		
	}
	
	private List<Long> getIdsByListProducts(List<Product> products) {
		
		log.debug("PurchaseService.getIdsByListProducts - Start");
		
		return products.stream().map(product -> product.getId()).collect(Collectors.toList());
				
	}
	
	private List<Product> getProductsPurchase(List<Long> products) {
		
		log.debug("PurchaseService.getProductsPurchase - Start - ids:  [{}]", products);
		
		List<Product> response = new ArrayList<>();
		
		products.forEach(productRequestDTO -> {
			
			Product product = getProductById(productRequestDTO);
			
			response.add(product);
			
		});
		
		log.debug("PurchaseService.getProductsPurchase - Finish - ids:  [{}], Response: [{}]", products, response);
		
		return response;
		
	}
	
	private Product getProductById(Long id) {
		
		log.debug("PurchaseService.getProductById - Start - id:  [{}]", id);
		
		Optional<Product> product = productRepository.findById(id);
		
		if(product.isPresent()) {
			
			return product.get();
			
		}
		
		log.debug("PurchaseService.getProductById - Finish - id:  [{}]", id);
		
		throw new ProductNotExistsException(String.format(PRODUCT_WITH_ID_NOT_EXISTS, id));
		
	}

}
