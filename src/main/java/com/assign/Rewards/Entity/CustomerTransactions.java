package com.assign.Rewards.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CustomerTransactions {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
    private BigDecimal amount;
    private LocalDate transactionDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="customer_id")
    private Customer customer;

	public Long getTransactionId() {
		return transactionId;
	}

	public CustomerTransactions() {}
	
	public CustomerTransactions(Long transactionId, BigDecimal amount, LocalDate transactionDate, Customer customer) {
		super();
		this.transactionId = transactionId;
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.customer = customer;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
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

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	
}
