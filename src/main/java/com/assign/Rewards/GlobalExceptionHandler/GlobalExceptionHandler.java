package com.assign.Rewards.GlobalExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.assign.Rewards.Model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(CustomerNotFound.class)
	public ResponseEntity noCustomerFound(CustomerNotFound ex) {
		
		ErrorResponse er=new ErrorResponse(ex.getMessage(),System.currentTimeMillis());
		return new ResponseEntity<>(er,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(TransactionsNotFound.class)
	public ResponseEntity noTransactionFound(TransactionsNotFound ex) {
		
		ErrorResponse er=new ErrorResponse(ex.getMessage(),System.currentTimeMillis());
		return new ResponseEntity<>(er,HttpStatus.NOT_FOUND);
		
	}

}
