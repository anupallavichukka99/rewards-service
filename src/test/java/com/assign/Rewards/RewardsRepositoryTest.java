package com.assign.Rewards;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.assign.Rewards.GlobalExceptionHandler.TransactionsNotFound;
import com.assign.Rewards.Model.Transactions;
import com.assign.Rewards.Repository.RewardsRepository;

@DataJpaTest
class RewardsRepositoryTest {

	@Autowired
    private RewardsRepository repository;

	@Test
	void testAllTransactions() {
	
		assertEquals( repository.getAllTransactions().size(),10);
	}
	
	@Test
	void testFindBycustomerId() {
	
		List<Transactions>txn=repository.findBycustomerId(102L).get();
		assertEquals(3,txn.size());
	}

	@Test
	void testNoCustomerFound() {
		
		List<Transactions>txn=repository.findBycustomerId(109L).orElse(null);
		assertEquals(0,txn.size());
		
	}
}
