package com.delivery.api.delivery.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delivery.api.delivery.dto.product.request.ProductRequestDTO;
import com.delivery.api.delivery.dto.product.response.ProductSavedResponseDTO;
import com.delivery.api.delivery.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/v1/product")
@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	@CacheEvict(value = "listProducts", allEntries = true)
	public ResponseEntity<ProductSavedResponseDTO> saveProduct(@Valid @RequestBody ProductRequestDTO request) {
		
		log.debug("ProductController.saveProduct - Start - Request:  [{}]", request);
		
		ProductSavedResponseDTO productSaved = productService.saveProduct(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productSaved.getId())
				.toUri();
		
		ResponseEntity<ProductSavedResponseDTO> response = ResponseEntity.created(uri).body(productSaved);
		
		log.debug("ProductController.saveProduct - Finish -  Request:  [{}], Response:  [{}]", request, response);
		
		return response;
	}

}
