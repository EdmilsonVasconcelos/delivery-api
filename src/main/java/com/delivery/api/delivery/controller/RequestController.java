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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delivery.api.delivery.dto.requestEntity.request.RequestDTO;
import com.delivery.api.delivery.dto.requestEntity.response.RequestResponseDTO;
import com.delivery.api.delivery.service.RequestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/v1/request")
@RestController
public class RequestController {
	
	@Autowired
	private RequestService requestService;
	
	@GetMapping
	@Cacheable(value = "listRequests")
	public ResponseEntity<List<RequestResponseDTO>> getAllRequests() {
		
		log.debug("RequestController.getAllRequests - Start ");
		
		List<RequestResponseDTO> response = requestService.getAllRequests();
		
		log.debug("RequestController.getAllRequests - Finish - Response:  [{}]", response);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PostMapping
	@CacheEvict(value = "listRequests", allEntries = true)
	public ResponseEntity<RequestResponseDTO> saveRequest(@Valid @RequestBody RequestDTO request) {
		
		log.debug("RequestController.saveRequest - Start - Request:  [{}]", request);
		
		RequestResponseDTO requestSaved = requestService.saveRequest(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(requestSaved.getId())
				.toUri();
		
		ResponseEntity<RequestResponseDTO> response = ResponseEntity.created(uri).body(requestSaved);
		
		log.debug("RequestController.saveRequest - Finish -  Request:  [{}], Response:  [{}]", request, response);
		
		return response;
	}

}
