package com.delivery.api.delivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.delivery.converter.request.Converter;
import com.delivery.api.delivery.dto.customer.response.CustomerResponseDTO;
import com.delivery.api.delivery.dto.requestEntity.request.RequestDTO;
import com.delivery.api.delivery.dto.requestEntity.response.RequestResponseDTO;
import com.delivery.api.delivery.exception.ProductNotExistsException;
import com.delivery.api.delivery.model.Customer;
import com.delivery.api.delivery.model.Product;
import com.delivery.api.delivery.model.Request;
import com.delivery.api.delivery.repository.ProductRepository;
import com.delivery.api.delivery.repository.RequestRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RequestService {
	
	private static final String PRODUCT_WITH_ID_NOT_EXISTS = "Produto com id %s nao existe";
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public RequestResponseDTO saveRequest(RequestDTO request) {
		
		log.debug("RequestService.saveRequest - Start - Request:  [{}]", request);
		
		Request requestToSave = Converter.toRequest(request, mapper.map(request.getCustomer(), Customer.class), getProductsRequest(request.getProducts()));
		
		Request requestSaved = requestRepository.save(requestToSave);
		
		RequestResponseDTO response = Converter.toRequestResponseDTO(requestSaved, mapper.map(request.getCustomer(), CustomerResponseDTO.class), request.getProducts());
		
		log.debug("RequestService.saveRequest - Finish - Request [{}], Response:  [{}]", request, response);
		
		return response;
		
	}
	
	public List<RequestResponseDTO> getAllRequests() {
		
		log.debug("RequestService.getAllRequests - Start");
		
		List<Request> allRequests = requestRepository.findByIsOpenTrue();
		
		List<RequestResponseDTO> response = new ArrayList<>();
		
		allRequests.forEach(request -> {
			
			RequestResponseDTO requestResponseDTO = Converter.toRequestResponseDTO(request, mapper.map(request.getCustomer(), CustomerResponseDTO.class), getIdsByListProducts(request.getProducts()));
			
			response.add(requestResponseDTO);
			
		});
		
		log.debug("RequestService.getAllRequests - Finish -  Response:  [{}]", response);
		
		return response;
		
	}
	
	private List<Long> getIdsByListProducts(List<Product> products) {
		
		log.debug("RequestService.getIdsByListProducts - Start");
		
		return products.stream().map(product -> product.getId()).collect(Collectors.toList());
				
	}
	
	private List<Product> getProductsRequest(List<Long> products) {
		
		log.debug("RequestService.getProductsRequest - Start - ids:  [{}]", products);
		
		List<Product> response = new ArrayList<>();
		
		products.forEach(productRequestDTO -> {
			
			Product product = getProductById(productRequestDTO);
			
			response.add(product);
			
		});
		
		log.debug("RequestService.getProductsRequest - Finish - ids:  [{}], Response: [{}]", products, response);
		
		return response;
		
	}
	
	private Product getProductById(Long id) {
		
		log.debug("RequestService.getProductById - Start - id:  [{}]", id);
		
		Optional<Product> product = productRepository.findById(id);
		
		if(product.isPresent()) {
			
			return product.get();
			
		}
		
		log.debug("RequestService.getProductById - Finish - id:  [{}]", id);
		
		throw new ProductNotExistsException(String.format(PRODUCT_WITH_ID_NOT_EXISTS, id));
		
	}

}
