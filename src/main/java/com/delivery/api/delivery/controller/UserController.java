package com.delivery.api.delivery.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delivery.api.delivery.dto.user.request.SaveUserDTO;
import com.delivery.api.delivery.dto.user.response.UserSavedDTO;
import com.delivery.api.delivery.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/v1/user")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<UserSavedDTO> saveUser(@Valid @RequestBody SaveUserDTO request) {
		
		log.debug("UserController.saveUser - Start - Request:  [{}]", request);
		
		UserSavedDTO userSaved = userService.saveUser(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userSaved.getId())
				.toUri();
		
		ResponseEntity<UserSavedDTO> response = ResponseEntity.created(uri).body(userSaved);
		
		log.debug("UserController.saveUser - Finish -  Request:  [{}], Response:  [{}]", request, response);
		
		return response;
	}

}
