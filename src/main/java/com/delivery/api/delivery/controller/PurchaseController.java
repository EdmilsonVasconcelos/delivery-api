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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delivery.api.delivery.dto.purchase.request.PurchaseRequestDTO;
import com.delivery.api.delivery.dto.purchase.response.PurchaseResponseDTO;
import com.delivery.api.delivery.service.PurchaseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/v1/purchase")
@RestController
public class PurchaseController {
	
	@Autowired
	private PurchaseService requestService;
	
	@GetMapping
	@Cacheable(value = "listPurchases")
	public ResponseEntity<List<PurchaseResponseDTO>> getAllPurchases() {
		
		log.debug("PurchaseController.getAllPurchases - Start ");
		
		List<PurchaseResponseDTO> response = requestService.getAllpurchases();
		
		log.debug("PurchaseController.getAllPurchases - Finish - Response:  [{}]", response);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping
	@CacheEvict(value = "listPurchases", allEntries = true)
	public ResponseEntity<PurchaseResponseDTO> savePurchase(@Valid @RequestBody PurchaseRequestDTO request) {
		
		log.debug("PurchaseController.savePurchase - Start - Request:  [{}]", request);
		
		PurchaseResponseDTO requestSaved = requestService.savePurchase(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requestSaved.getId())
				.toUri();
		
		ResponseEntity<PurchaseResponseDTO> response = ResponseEntity.created(uri).body(requestSaved);
		
		log.debug("PurchaseController.savePurchase - Finish -  Request:  [{}], Response:  [{}]", request, response);
		
		return response;
	}
	
	@PutMapping(value = "/alter-status-purchase")
	@CacheEvict(value = "listPurchases", allEntries = true)
	public ResponseEntity<PurchaseResponseDTO> alterStatusPurchase(@RequestParam Long idPurchase) {
		
		log.debug("PurchaseController.alterStatusPurchase - Start - id: [{}]", idPurchase);
		
		PurchaseResponseDTO purchaseClosed = requestService.alterStatusPurchase(idPurchase);
		
		ResponseEntity<PurchaseResponseDTO> response = ResponseEntity.ok(purchaseClosed);
		
		log.debug("PurchaseController.alterStatusPurchase - Start - id: [{}], response: [{}]", idPurchase, response);
		
		return response;
	}

}
