package com.assign.Rewards.Controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.assign.Rewards.Response.MonthlyRewardResponse;
import com.assign.Rewards.Response.RewardsResponse;
import com.assign.Rewards.Service.RewardsServiceImpl;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

/**
 * REST controller for managing customer rewards and transactions.
 * Provides endpoints to retrieve customer rewards, monthly rewards,and reward details for a specific customer.
 */

@RestController
@RequestMapping("/api")
@Validated
public class RewardsController {

    private final RewardsServiceImpl rewardService;

    public RewardsController(RewardsServiceImpl rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Retrieves reward details for all customers based on transactions from the last three months.
     * @return list of customer reward summaries
     */
    	
    @GetMapping("/rewards")
    public List<RewardsResponse> getAllCustomersRewards() {
        return rewardService.getAllCustomersRewards();
    }

    /**
     * Retrieves reward details for all customers for a given month.
     * @param date the month in yyyy-MM format (e.g. 2026-01)
     * @return list of monthly reward details for all customers
     */
    @GetMapping("/monthWiseRewards")
    public List<MonthlyRewardResponse> getCustomerRewardDetailsMonthWise(@RequestParam 
    		@Pattern(
    	    regexp = "^\\d{4}-(0[1-9]|1[0-2])$",
    	    message = "Date must be in yyyy-MM format"
    	)String date) {

        return rewardService.getCustomerRewardDetailsMonthWise(date);
    }

    /**
     * Retrieves reward details for a specific customer based on transactions from the last three months.
     * @param id the unique customer identifier
     * @return reward details of the specified customer 
     */
    @GetMapping("/customerId/{id}")
    public RewardsResponse getRewardsBycustomerId(@PathVariable @Positive Long id) {

        return rewardService.getRewardsBycustomerId(id);
    }
}