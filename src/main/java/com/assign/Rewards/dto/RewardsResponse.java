package com.assign.Rewards.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RewardsResponse {

	private Long customerId;
	private String customerName;
    private List<MonthlyRewards> monthlyRewards;
    private long totalRewards;
    private BigDecimal totalamount;

 
}

