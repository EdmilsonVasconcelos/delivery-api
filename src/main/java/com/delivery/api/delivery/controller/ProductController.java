package com.delivery.api.delivery.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delivery.api.delivery.dto.product.request.ProductToSaveRequestDTO;
import com.delivery.api.delivery.dto.product.request.ProductUpdateRequestDTO;
import com.delivery.api.delivery.dto.product.response.ProductReponseDTO;
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
	public ResponseEntity<ProductReponseDTO> saveProduct(@Valid @RequestBody ProductToSaveRequestDTO request) {
		
		log.debug("ProductController.saveProduct - Start - Request:  [{}]", request);
		
		ProductReponseDTO productSaved = productService.saveProduct(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productSaved.getId())
				.toUri();
		
		ResponseEntity<ProductReponseDTO> response = ResponseEntity.created(uri).body(productSaved);
		
		log.debug("ProductController.saveProduct - Finish -  Request:  [{}], Response:  [{}]", request, response);
		
		return response;
	}
	
	@PutMapping
	@CacheEvict(value = "listProducts", allEntries = true)
	public ResponseEntity<ProductReponseDTO> updateProduct(@Valid @RequestBody ProductUpdateRequestDTO request) {
		
		log.debug("ProductController.updateProduct - Start - Request");
		
		ProductReponseDTO productUpdated = productService.updateproduct(request);
		
		ResponseEntity<ProductReponseDTO> response = ResponseEntity.ok(productUpdated);
		
		log.debug("ProductController.updateProduct - Finish -  Request:  [{}], Response:  [{}]", response);
		
		return response;
	}

}
