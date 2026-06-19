package com.assign.Rewards.Entity;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private long customerId;
	private String customerName;
	
	@OneToMany(mappedBy = "customer",fetch = FetchType.LAZY)
	private List<CustomerTransactions> customerTransaction;




}
