package com.assign.Rewards.Response;

import java.math.BigDecimal;
import java.util.List;

import com.assign.Rewards.Model.MonthlyRewards;



public class RewardsResponse {

	private Long customerId;
	private String customerName;
    private List<MonthlyRewards> monthlyRewards;
    private long totalRewards;
    private BigDecimal totalamount;



    public RewardsResponse(Long customerId,
                                  String customerName,
                                  List<MonthlyRewards> monthlyRewards,
                                  long totalRewards,BigDecimal totalamount) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.monthlyRewards = monthlyRewards;
        this.totalRewards = totalRewards;
        this.totalamount=totalamount;
    }



	public Long getCustomerId() {
		return customerId;
	}



	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}



	public String getCustomerName() {
		return customerName;
	}



	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	public List<MonthlyRewards> getMonthlyRewards() {
		return monthlyRewards;
	}



	public void setMonthlyRewards(List<MonthlyRewards> monthlyRewards) {
		this.monthlyRewards = monthlyRewards;
	}



	public long getTotalRewards() {
		return totalRewards;
	}



	public void setTotalRewards(long totalRewards) {
		this.totalRewards = totalRewards;
	}



	public BigDecimal getTotalamount() {
		return totalamount;
	}



	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}
 
}

