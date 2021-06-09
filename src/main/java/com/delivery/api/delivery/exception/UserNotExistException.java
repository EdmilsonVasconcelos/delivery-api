package com.delivery.api.delivery.exception;

public class UserNotExistException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public UserNotExistException(String message){
        super(message);
    }

}