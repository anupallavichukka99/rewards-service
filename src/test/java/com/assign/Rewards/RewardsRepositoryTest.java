package com.assign.Rewards;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.assign.Rewards.Entity.Customer;
import com.assign.Rewards.Entity.CustomerTransactions;
import com.assign.Rewards.Repository.CustomerRepository;
import com.assign.Rewards.Repository.CustomerTransactionRepository;


/**
 * Test class for validating JPA repository layer functionality in the Rewards application. 
 *   Configures in-memory database (usually H2)
 *   Scans only repository layer
 *   Does not load full Spring Boot context
 * 
 */

@DataJpaTest
class RewardsRepositoryTest {

	@Autowired
    private CustomerRepository customerRepository;
	
	@Autowired
    private CustomerTransactionRepository tansactionRepository;

	@Test
	void testAllTransactions() {
	
		assertEquals(7,tansactionRepository.findAll().size());
	}
	
	@Test
	void testFindBycustomerId() {
	
		Customer txn=customerRepository.findById(103L).orElse(null);
		assertEquals("Siva",txn.getCustomerName());
	}

	@Test
	void testNoCustomerFound() {

	    Customer customer =customerRepository.findById(109L).orElse(null);

	    assertNull(customer);
	}
	@Test
	void testFindByMonthAndYear() {

		List<CustomerTransactions> tnxs= tansactionRepository.findByMonthAndYear(5, 2026);
		assertEquals(3,tnxs.size());
	}
	
}
