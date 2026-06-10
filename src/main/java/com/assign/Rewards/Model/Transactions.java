package com.assign.Rewards.Model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Transactions {

	@Id
	private Long transactionId;
    private Long customerId;
    private String customerName;
    private BigDecimal amount;
    private LocalDate transactionDate;

    public Transactions() {
    }

    public Transactions(Long transactionId, Long customerId,
                       String customerName, BigDecimal amount,
                       LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", customerId=" + customerId + ", customerName="
				+ customerName + ", amount=" + amount + ", transactionDate=" + transactionDate + "]";
	}
    

}
