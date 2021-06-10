package com.delivery.api.delivery.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.delivery.api.delivery.dto.user.request.ChangePasswordRequestDTO;
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
	
	@GetMapping
	@Cacheable(value = "listUsers")
	public ResponseEntity<List<UserSavedDTO>> getAllUsers() {
		
		log.debug("UserController.getAllUsers - Start ");
		
		List<UserSavedDTO> response = userService.getAllUsers();
		
		log.debug("UserController.getAllUsers - Finish - Response:  [{}]", response);
		
		return ResponseEntity.ok(response);
		
	}
	
	@PutMapping(value = "/change-password")
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<UserSavedDTO> udpdatePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
		
		log.debug("UserController.udpdatePassword - Start - Request");
		
		UserSavedDTO userSaved = userService.changePassword(request);
		
		ResponseEntity<UserSavedDTO> response = ResponseEntity.ok(userSaved);
		
		log.debug("UserController.udpdatePassword - Finish -  Request:  [{}], Response:  [{}]", response);
		
		return response;
	}
	
	@PostMapping
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<UserSavedDTO> saveUser(@Valid @RequestBody SaveUserDTO request) {
		
		log.debug("UserController.saveUser - Start - Request:  [{}]", request);
		
		UserSavedDTO userSaved = userService.saveUser(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userSaved.getId())
				.toUri();
		
		ResponseEntity<UserSavedDTO> response = ResponseEntity.created(uri).body(userSaved);
		
		log.debug("UserController.saveUser - Finish -  Request:  [{}], Response:  [{}]", request, response);
		
		return response;
	}
	
	@DeleteMapping
	@CacheEvict(value = "listUsers", allEntries = true)
	public ResponseEntity<UserSavedDTO> deleteUser(@RequestParam Long idUser) {
		
		log.debug("UserController.deleteBrand - Start - idUser: []", idUser);
		
		userService.deleteUser(idUser);
		
		log.debug("UserController.deleteBrand - Finish - idUser: []", idUser);
		
		return ResponseEntity.noContent().build();
	} 

}
