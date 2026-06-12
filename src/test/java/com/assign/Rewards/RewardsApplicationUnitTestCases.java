package com.assign.Rewards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.assign.Rewards.Entity.Customer;
import com.assign.Rewards.Entity.CustomerTransactions;
import com.assign.Rewards.GlobalExceptionHandler.CustomerNotFound;
import com.assign.Rewards.Repository.CustomerRepository;
import com.assign.Rewards.Repository.CustomerTransactionRepository;
import com.assign.Rewards.Response.MonthlyRewardResponse;
import com.assign.Rewards.Response.RewardsResponse;
import com.assign.Rewards.Service.RewardsServiceImpl;

/**
* Unit test class for RewardsServiceImpl.
* 
* This class contains test cases to verify customer reward calculations,monthly reward summaries, transaction filtering, and exception handling.
*
* The test cases cover:
* 
*     Retrieving all customer details
*     Calculating rewards for a specific customer
*     Calculating rewards for all customers
*     Generating month-wise reward summaries
*     Filtering transactions within the last three months
*     Reward points calculation logic
*     Customer not found scenarios
*
* Mockito is used to mock repository dependencies 
*/

@ExtendWith(MockitoExtension.class)
class RewardsApplicationUnitTestCases {


	 	@Mock
	    private CustomerRepository customerRepo;

	    @Mock
	    private CustomerTransactionRepository transactionsRepo;

	    @InjectMocks
	    private RewardsServiceImpl rewardService;


	    private List<Customer> customers;
	    private List<CustomerTransactions> transactions;

	    @BeforeEach
	    void setup() {

	        Customer customer1 = new Customer();
	        customer1.setCustomerId(101L);
	        customer1.setCustomerName("Anu");

	        Customer customer2 = new Customer();
	        customer2.setCustomerId(102L);
	        customer2.setCustomerName("Siva");

	        Customer customer3 = new Customer(103L,"Dhanvika",null);

	        CustomerTransactions txn1 = new CustomerTransactions();
	        txn1.setTransactionId(1L);
	        txn1.setAmount(new BigDecimal("120"));
	        txn1.setTransactionDate(LocalDate.now().minusMonths(1));
	        txn1.setCustomer(customer1);

	        CustomerTransactions txn2 = new CustomerTransactions();
	        txn2.setTransactionId(2L);
	        txn2.setAmount(new BigDecimal("80"));
	        txn2.setTransactionDate(LocalDate.now().minusMonths(2));
	        txn2.setCustomer(customer1);

	        CustomerTransactions txn3 = new CustomerTransactions();
	        txn3.setTransactionId(3L);
	        txn3.setAmount(new BigDecimal("140"));
	        txn3.setTransactionDate(LocalDate.now().minusMonths(1));
	        txn3.setCustomer(customer2);

	        CustomerTransactions txn4 = new CustomerTransactions();
	        txn4.setTransactionId(4L);
	        txn4.setAmount(new BigDecimal("200"));
	        txn4.setTransactionDate(LocalDate.now().minusMonths(2));
	        txn4.setCustomer(customer2);

	        CustomerTransactions txn5 = new CustomerTransactions();
	        txn5.setTransactionId(5L);
	        txn5.setAmount(new BigDecimal("180"));
	        txn5.setTransactionDate(LocalDate.now().minusMonths(1));
	        txn5.setCustomer(customer3);

	        customer1.setCustomerTransaction(List.of(txn1, txn2));
	        customer2.setCustomerTransaction(List.of(txn3, txn4));
	        customer3.setCustomerTransaction(List.of(txn5));

	        customers = List.of(customer1, customer2, customer3);

	        transactions = List.of(txn1, txn2,txn3, txn4,txn5);
	    }
	    
	    @Test
	    void testGetAllCustomerRewardDetails() {

	        when(customerRepo.findAll()).thenReturn(customers);

	        List<Customer> result =rewardService.getAllCustomersRewardDetailsFromDB();

	        assertEquals(3, result.size());
	        assertEquals("Anu", result.get(0).getCustomerName());
	        assertEquals("Siva", result.get(1).getCustomerName());
	        assertEquals("Dhanvika", result.get(2).getCustomerName());
	    }
	    
	    @Test
	    void testGetRewardDetailsForCustomer() {

	        when(customerRepo.findById(102L)).thenReturn(Optional.of(customers.get(1)));

	        RewardsResponse result =rewardService.getRewardsBycustomerId(102L);

	        assertEquals(102L, result.getCustomerId());
	        assertEquals("Siva", result.getCustomerName());
	        assertTrue(result.getTotalRewards() > 0);
	    }
	    
	    @Test
	    void testGetAllCustomersRewards() {

	        when(customerRepo.findAll()).thenReturn(customers);

	        List<RewardsResponse> result = rewardService.getAllCustomersRewards();

	        assertEquals(3, result.size());

	        assertEquals("Anu",
	                result.get(0).getCustomerName());

	        assertEquals("Siva",
	                result.get(1).getCustomerName());

	        assertEquals("Dhanvika",
	                result.get(2).getCustomerName());
	    }
	    
	    @Test
	    void testGetRewardDetailsMonthWise() {

	        transactions.forEach( txn -> txn.setTransactionDate(LocalDate.of(2026, 5, 10)));

	        when(transactionsRepo.findByMonthAndYear(5, 2026)).thenReturn(transactions);

	        List<MonthlyRewardResponse> result = rewardService.getCustomerRewardDetailsMonthWise("2026-05");

	        assertEquals(3, result.size());

	        assertEquals(101L,result.get(0).getCustomerId());

	        assertEquals(102L,result.get(1).getCustomerId());

	        assertEquals(103L,result.get(2).getCustomerId());
	    }
	    
	    @Test
	    void testGetTransactionDuring3MonthPeriod() {

	        List<CustomerTransactions> result =rewardService.getTransactionsDuring3MonthPeriod(transactions);

	        assertEquals(5, result.size());
	    }
	    
	    @Test
	    void testGetCustomerRewardResponse() {

	        RewardsResponse result =rewardService.getCustomerRewardResponse(
	                        customers.get(0).getCustomerTransaction(),
	                        customers.get(0).getCustomerId(),
	                        customers.get(0).getCustomerName());

	        assertEquals(101L, result.getCustomerId());
	        assertEquals("Anu", result.getCustomerName());

	        assertNotNull(result.getMonthlyRewards());
	        assertTrue(result.getTotalRewards() > 0);
	    }
	    
	    @Test
	    void testCalculateRewardPoints() {

	        Long points =rewardService.calculateRewardPoints( new BigDecimal("120"));

	        assertEquals(90L, points);
	    }
	    
	    @Test
	    void testGetRewardDetailsForCustomerNotFound() {

	        when(customerRepo.findById(999L)).thenReturn(Optional.empty());

	        assertThrows( CustomerNotFound.class,
	                () -> rewardService.getRewardsBycustomerId(999L)
	        );
	    }
	}
   




