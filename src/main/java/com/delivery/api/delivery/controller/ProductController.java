package com.delivery.api.delivery.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delivery.api.delivery.dto.product.request.ProductToSaveRequestDTO;
import com.delivery.api.delivery.dto.product.request.ProductToUpdateRequestDTO;
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
		
		var response = productService.getAllproducts();
		
		log.debug("ProductController.getAllproducts - Finish - Response:  [{}]", response);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping
	@CacheEvict(value = "listProducts", allEntries = true)
	public ResponseEntity<ProductReponseDTO> saveProduct(@Valid @RequestBody ProductToSaveRequestDTO request) {
		
		log.debug("ProductController.saveProduct - Start - Request:  [{}]", request);
		
		var productSaved = productService.saveProduct(request);
		
		var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(productSaved.getId())
				.toUri();
		
		ResponseEntity<ProductReponseDTO> response = ResponseEntity.created(uri).body(productSaved);
		
		log.debug("ProductController.saveProduct - Finish -  Request:  [{}], Response:  [{}]", request, response);
		
		return response;
	}
	
	@PutMapping
	@CacheEvict(value = "listProducts", allEntries = true)
	public ResponseEntity<ProductReponseDTO> updateProduct(@Valid @RequestBody ProductToUpdateRequestDTO request) {
		
		log.debug("ProductController.updateProduct - Start - Request");
		
		var productUpdated = productService.updateproduct(request);
		
		var response = ResponseEntity.ok(productUpdated);
		
		log.debug("ProductController.updateProduct - Finish -  Request:  [{}], Response:  [{}]", response);
		
		return response;
	}
	
	@DeleteMapping
	@CacheEvict(value = "listProducts", allEntries = true)
	public ResponseEntity<ProductReponseDTO> deleteProduct(@RequestParam Long idProduct) {
		
		log.debug("ProductController.deleteProduct - Start - idProduct: []", idProduct);
		
		productService.deleteProduct(idProduct);
		
		log.debug("ProductController.deleteProduct - Finish - idProduct: []", idProduct);
		
		return ResponseEntity.noContent().build();
	}

	@GetMapping("{idProduct}")
	@CacheEvict(value = "listProducts", allEntries = true)
	public ResponseEntity<ProductReponseDTO> getProductById(@PathVariable Long idProduct) {

		log.debug("ProductController.getProductById - Start - idProduct: []", idProduct);

		var response = productService.getProductById(idProduct);

		log.debug("ProductController.getProductById - Finish - idProduct: [], response: [{}]", idProduct, response);

		return ResponseEntity.ok(response);
	}

}
