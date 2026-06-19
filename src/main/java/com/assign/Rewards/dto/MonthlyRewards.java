package com.assign.Rewards.dto;

import java.math.BigDecimal;
import java.time.YearMonth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyRewards {

	private YearMonth month;
    private long points;
    private BigDecimal amount;

    
}

