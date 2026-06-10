package com.assign.Rewards.Model;

public class ErrorResponse {
	
	private String message;
	private long timeStamp;
	
	public ErrorResponse(String message, long timeStamp) {
		super();
		this.message = message;
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}


	public long getTimeStamp() {
		return timeStamp;
	}


}
