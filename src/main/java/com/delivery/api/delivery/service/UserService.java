package com.delivery.api.delivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.delivery.api.delivery.dto.user.request.SaveUserDTO;
import com.delivery.api.delivery.dto.user.response.UserSavedDTO;
import com.delivery.api.delivery.exception.UserExistsException;
import com.delivery.api.delivery.exception.UserNotExistException;
import com.delivery.api.delivery.model.User;
import com.delivery.api.delivery.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	
	private static final String USER_WITH_EMAIL_EXIST = "User with email %s exist.";
	
	private static final String USER_WITH_EMAIL_NOT_EXIST = "User with email %s not exist.";
	
	private static final String USER_WITH_ID_NOT_EXIST = "User with id %s not exist.";
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepository;
	
	public List<UserSavedDTO> getAllUsers() {
		
		log.debug("UserService.getAllUsers - Start - Request:  [{}]");
		
		List<User> users = userRepository.findAll();
		
		List<UserSavedDTO> response = new ArrayList<>();
		
		users.forEach(user -> {
			UserSavedDTO userSaved = mapper.map(user, UserSavedDTO.class);
			
			response.add(userSaved);
		});
		
		
		log.debug("UserService.getAllUsers - Finish - Response:  [{}]", response);
		
		return response;
		
	}
	
	public UserSavedDTO saveUser(SaveUserDTO request) {
		
		log.debug("UserService.saveUser - Start - Request:  [{}]", request);
		
		checkExistUser(request.getEmail());
		
		User userToSave = mapper.map(request, User.class);
		
		userToSave.setPassword(new BCryptPasswordEncoder().encode(userToSave.getPassword().toString()));
		
		User userSaved = userRepository.save(userToSave);
		
		UserSavedDTO response = mapper.map(userSaved, UserSavedDTO.class);
		
		log.debug("UserService.saveUser - Finish - Request:  [{}], Response:  [{}]", request, response);
		
		return response;
		
	}
	
	public void deleteUser(Long idUser) {
		
		log.debug("UserService.deleteUser - Start - idUser:  [{}]", idUser);
		
		checkExistUser(idUser);
		
		userRepository.deleteById(idUser);
		
		log.debug("UserService.deleteUser - Finish - idUser:  [{}]", idUser);
		
	}
	

	private void checkExistUser(String email) {
		
		log.debug("UserService.checkExistUser - Start - Request:  [{}]", email);
		
		Optional<User> user = userRepository.findByEmail(email);
		
		if(user.isPresent()) {
			
			throw new UserExistsException(String.format(USER_WITH_EMAIL_EXIST, email));
			
		}
		
		log.debug("UserService.checkExistUser - Finish:  [{}]", email);
		
		
	}
	
	private void checkExistUser(Long idUser) {
		
		log.debug("UserService.checkExistUser - Start - idUser: [{}]", idUser);
		
		Optional<User> user = userRepository.findById(idUser);
		
		if(!user.isPresent()) {
			
			throw new UserNotExistException(String.format(USER_WITH_ID_NOT_EXIST, idUser));
			
		}
		
		log.debug("UserService.checkExistUser - Finish: idUser: [{}]", idUser);
		
		
	}
}
