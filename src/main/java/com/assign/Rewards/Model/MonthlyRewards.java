package com.assign.Rewards.Model;

import java.math.BigDecimal;
import java.time.YearMonth;

public class MonthlyRewards {

	private YearMonth month;
    private long points;
    private BigDecimal amount;

 

    public MonthlyRewards(YearMonth month, long points,BigDecimal amount) {
        this.month = month;
        this.points = points;
        this.amount=amount;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
    
    
}

