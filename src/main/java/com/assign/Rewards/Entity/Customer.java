package com.assign.Rewards.Entity;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private long customerId;
	private String customerName;
	
	@OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
	private List<CustomerTransactions> customerTransaction;

	public Customer() {}

	public Customer(long customerId, String customerName, List<CustomerTransactions> customerTransaction) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerTransaction = customerTransaction;
	}
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public List<CustomerTransactions> getCustomerTransaction() {
		return customerTransaction;
	}

	public void setCustomerTransaction(List<CustomerTransactions> customerTransaction) {
		this.customerTransaction = customerTransaction;
	}


}
