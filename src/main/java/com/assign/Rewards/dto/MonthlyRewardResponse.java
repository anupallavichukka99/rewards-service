package com.assign.Rewards.dto;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public class MonthlyRewardResponse {

	private Long customerId;
	private String customerName;
	private YearMonth month;
    private long rewards;
    private BigDecimal amount;
    
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public MonthlyRewardResponse(Long customerId, String customerName, YearMonth month, long rewards,
			BigDecimal amount) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.month = month;
		this.rewards = rewards;
		this.amount = amount;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public YearMonth getMonth() {
		return month;
	}
	public void setMonth(YearMonth month) {
		this.month = month;
	}
	public long getRewards() {
		return rewards;
	}
	public void setRewards(long rewards) {
		this.rewards = rewards;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
