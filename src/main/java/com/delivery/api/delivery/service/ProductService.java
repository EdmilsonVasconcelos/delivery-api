package com.delivery.api.delivery.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.delivery.dto.product.request.ProductRequestDTO;
import com.delivery.api.delivery.dto.product.response.ProductSavedResponseDTO;
import com.delivery.api.delivery.exception.ProductExistsException;
import com.delivery.api.delivery.model.Product;
import com.delivery.api.delivery.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	
	private static final String PRODUCT_WITH_NAME_EXISTS = "Product with name %s exists.";
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public ProductSavedResponseDTO saveProduct(ProductRequestDTO request) {
		
		log.debug("ProductService.saveProduct - Start - Request:  [{}]", request);
		
		checkIfExistsProduct(request.getName());
		
		Product productToSave = mapper.map(request, Product.class);
		
		Product productSaved = productRepository.save(productToSave);
		
		ProductSavedResponseDTO response = mapper.map(productSaved, ProductSavedResponseDTO.class);
		
		log.debug("ProductService.saveProduct - Start - Request [{}], Response:  [{}]", request, response);
		
		return response;
		
	}
	
	private void checkIfExistsProduct(String nameProduct) {
		
		log.debug("ProductService.checkExistsProduct - Start - Product:  [{}]", nameProduct);
		
		Optional<Product> product = productRepository.findByName(nameProduct);
		
		if(product.isPresent()) {
			throw new ProductExistsException(String.format(PRODUCT_WITH_NAME_EXISTS, nameProduct));
		}
		
		log.debug("ProductService.checkExistsProduct - Finish - Product:  [{}]", nameProduct);
		
	}

}
