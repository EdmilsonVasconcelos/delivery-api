package com.delivery.api.delivery.service;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.delivery.api.delivery.dto.product.request.ProductToSaveRequestDTO;
import com.delivery.api.delivery.dto.product.request.ProductUpdateRequestDTO;
import com.delivery.api.delivery.dto.product.response.ProductReponseDTO;
import com.delivery.api.delivery.exception.ProductExistsException;
import com.delivery.api.delivery.exception.ProductNotExistsException;
import com.delivery.api.delivery.model.Product;
import com.delivery.api.delivery.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	
	private static final String PRODUCT_WITH_NAME_EXISTS = "Produto com nome %s já existe";
	
	private static final String PRODUCT_WITH_ID_NOT_EXISTS = "Produto com id %s nao existe";
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	public ProductReponseDTO saveProduct(ProductToSaveRequestDTO request) {
		
		log.debug("ProductService.saveProduct - Start - Request:  [{}]", request);
		
		checkIfExistsProductByName(request.getName());
		
		Product productToSave = mapper.map(request, Product.class);
		
		Product productSaved = productRepository.save(productToSave);
		
		ProductReponseDTO response = mapper.map(productSaved, ProductReponseDTO.class);
		
		log.debug("ProductService.saveProduct - Start - Request [{}], Response:  [{}]", request, response);
		
		return response;
		
	}

	public ProductReponseDTO updateproduct(ProductUpdateRequestDTO request) {
		
		log.debug("ProductService.updateproduct - Start - Request:  [{}]", request);
		
		checkIfExistsProductById(request.getId());
		
		Product productToUpdate = mapper.map(request, Product.class);
		
		Product productSaved = productRepository.save(productToUpdate);
		
		ProductReponseDTO response = mapper.map(productSaved, ProductReponseDTO.class);
		
		log.debug("ProductService.updateproduct - Start - Request [{}], Response:  [{}]", request, response);
		
		return response;
	}
	
	private void checkIfExistsProductByName(String nameProduct) {
		
		log.debug("ProductService.checkExistsProduct - Start - Product:  [{}]", nameProduct);
		
		Optional<Product> product = productRepository.findByName(nameProduct);
		
		if(product.isPresent()) {
			throw new ProductExistsException(String.format(PRODUCT_WITH_NAME_EXISTS, nameProduct));
		}
		
		log.debug("ProductService.checkExistsProduct - Finish - Product:  [{}]", nameProduct);
		
	}
	
	private void checkIfExistsProductById(Long id) {
		
		log.debug("ProductService.checkExistsProduct - Start - Product:  [{}]", id);
		
		Optional<Product> product = productRepository.findById(id);
		
		if(!product.isPresent()) {
			throw new ProductNotExistsException(String.format(PRODUCT_WITH_ID_NOT_EXISTS, id));
		}
		
		log.debug("ProductService.checkExistsProduct - Finish - Product:  [{}]", id);
		
	}

}
