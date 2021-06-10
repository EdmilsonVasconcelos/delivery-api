package com.delivery.api.delivery.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping
	@Cacheable(value = "listProducts")
	public ResponseEntity<List<ProductReponseDTO>> getAllproducts() {
		
		log.debug("ProductController.getAllproducts - Start ");
		
		List<ProductReponseDTO> response = productService.getAllproducts();
		
		log.debug("ProductController.getAllproducts - Finish - Response:  [{}]", response);
		
		return ResponseEntity.ok(response);
		
	}
	
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
