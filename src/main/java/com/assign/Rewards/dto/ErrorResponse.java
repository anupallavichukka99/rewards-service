package com.assign.Rewards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	
	private String message;
	private long timeStamp;
	
}
