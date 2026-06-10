package com.assign.Rewards.GlobalExceptionHandler;

public class TransactionsNotFound extends RuntimeException{

	public TransactionsNotFound(String ex) {
		super(ex);
	}
}
