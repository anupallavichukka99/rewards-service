package com.assign.Rewards.Service;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.assign.Rewards.dto.MonthlyRewardResponse;
import com.assign.Rewards.dto.RewardsResponse;

public interface RewardService {

	 public List<MonthlyRewardResponse> getCustomerRewardDetailsMonthWise(@RequestParam String date);
	 public List<RewardsResponse> getAllCustomersRewards();
	  RewardsResponse getRewardsBycustomerId(@PathVariable Long id);
}
