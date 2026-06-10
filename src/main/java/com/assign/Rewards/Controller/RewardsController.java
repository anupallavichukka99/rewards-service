package com.assign.Rewards.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assign.Rewards.Model.Transactions;
import com.assign.Rewards.Response.RewardsResponse;
import com.assign.Rewards.Service.RewardsService;

/**
 * REST Controller for managing customer rewards and transactions.
 * Provides endpoints to fetch transaction details, monthly rewards,
 * and overall reward summary for customers.
 */

@RestController
@RequestMapping("/api")
public class RewardsController {

	  private final RewardsService rewardService;

	    public RewardsController(RewardsService rewardService) {
	        this.rewardService = rewardService;
	    }
	    
	    /**
	     * Calculates and returns reward summary for all customers during 3 month period.
	     * @return list of reward responses containing customer reward details
	     */
	    @GetMapping("/rewards")
	    public List<RewardsResponse> getRewards() {
	        return rewardService.getCustomerRewards();
	    }
	    
	    
	    /**
	     * Retrieves customers transactions and rewards filtered by month.
	     * @param date the year/month reference in string format (e.g., "2026-01")
	     * @return list of transactions for all customers for the given month
	     */    
	    @GetMapping("/monthWise")
	    public List<Transactions> monthWise(@RequestParam String date) {
	        return rewardService.getCustomerRewardsMonthWise(date);
	    }
	    
	    
	    /**
	     * Retrieves all transactions for a specific customer.
	     * @param id the customer unique identifier
	     * @return list of transactions for the given customer
	     */ 
	    @GetMapping("/customerId/{id}")
	    public List<Transactions> customerId(@PathVariable Long id) {
	        return rewardService.getTransactionByCustomerId(id);
	    }
	}