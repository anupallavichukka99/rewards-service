package com.assign.Rewards.GlobalExceptionHandler;

public class CustomerNotFound extends RuntimeException{

	public CustomerNotFound(String ex) {
		super(ex);
	}
}
