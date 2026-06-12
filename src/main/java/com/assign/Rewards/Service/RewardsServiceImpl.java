package com.assign.Rewards.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Service;
import com.assign.Rewards.Entity.Customer;
import com.assign.Rewards.Entity.CustomerTransactions;
import com.assign.Rewards.GlobalExceptionHandler.CustomerNotFound;
import com.assign.Rewards.GlobalExceptionHandler.TransactionsNotFound;
import com.assign.Rewards.Model.MonthlyRewards;
import com.assign.Rewards.Repository.CustomerRepository;
import com.assign.Rewards.Repository.CustomerTransactionRepository;
import com.assign.Rewards.Response.MonthlyRewardResponse;
import com.assign.Rewards.Response.RewardsResponse;


/**
 * Service class responsible for calculating and managing customer rewards.
 * Provides operations to retrieve customer reward details and reward points calculations.
 */

@Service
public class RewardsServiceImpl implements RewardService {

	@Autowired
	private final CustomerRepository customerRepo;
	
	@Autowired
	private final CustomerTransactionRepository transactionsRepo;

    public RewardsServiceImpl(CustomerRepository customerRepo,CustomerTransactionRepository transactionsRepo) {
        this.customerRepo = customerRepo;
        this.transactionsRepo=transactionsRepo;
    }
 
    /**
     * Retrieves reward details for a specific customer based on transactions from the last three months.
     * @param customerId the unique identifier of the customer
     * @return reward summary for the specified customer
     */
    @Override
    public RewardsResponse getRewardsBycustomerId(Long customerId){
    	
    	RewardsResponse response = null;
	
    	Customer customer = customerRepo.findById(customerId).orElseThrow(
    									() -> new CustomerNotFound("Customer not found : "+customerId));
    	
    	if(customer.getCustomerTransaction().size()==0) {
    		throw new TransactionsNotFound("Transactions are not found for this customer : "+customerId);
    	}
    	List<CustomerTransactions> transactions=getTransactionsDuring3MonthPeriod(customer.getCustomerTransaction());
    	response = getCustomerRewardResponse(transactions,customerId,customer.getCustomerName());
    
    	return response;
    }
   
    /**
     * Retrieves reward details for all customers for a given month.
     * @param date the month in yyyy-MM format
     * @return list of monthly reward summaries for all customers
     */
    @Override
    public List<MonthlyRewardResponse> getCustomerRewardDetailsMonthWise(String date){
    	
    	List<MonthlyRewardResponse> response = new ArrayList<>();
    	try {
    	
    	YearMonth ym=YearMonth.parse(date);
    	
    	List<CustomerTransactions> transactions = transactionsRepo.findByMonthAndYear(ym.getMonthValue(),ym.getYear());
    	  
   
    	  Map<Long, List<CustomerTransactions>> map = transactions.stream() .collect(Collectors.groupingBy(
    		                        t -> t.getCustomer().getCustomerId()));
    	  
    	  for (Map.Entry<Long, List<CustomerTransactions>> entry : map.entrySet()) {
    		  RewardsResponse rr=getCustomerRewardResponse(entry.getValue(),entry.getKey(),
    				  				entry.getValue().get(0).getCustomer().getCustomerName());
    		  response.add(new MonthlyRewardResponse(rr.getCustomerId(),rr.getCustomerName() , 
    				  ym, rr.getTotalRewards(), rr.getTotalamount()));
    	  }
    	}
    catch (Exception ex) {

        throw new RuntimeException("error occurred", ex);
    }
    
    	return response;
    }
    
    /**
     * Retrieves all customer records.
     * @return list of all customers
     */
    public List<Customer> getAllCustomersRewardDetailsFromDB(){
    	return this.customerRepo.findAll();
    }
    
    /**
     * Retrieves reward details for all customers based on transactions from the last three months.
     * @return list of customer reward summaries
     */
    @Override
    public List<RewardsResponse> getAllCustomersRewards(){
    	
    	 List<RewardsResponse> response = new ArrayList<>();
    	
    	try {
    	
    	List<Customer> customers= getAllCustomersRewardDetailsFromDB();
    	 
	
    	for(Customer customer:customers) {
 
    		List<CustomerTransactions> cr=customer.getCustomerTransaction();

        	if(cr.size()>0) {
        		 List<CustomerTransactions> transactions=getTransactionsDuring3MonthPeriod(cr);
         	    
         	    response.add(getCustomerRewardResponse(transactions,customer.getCustomerId(),customer.getCustomerName())); 				
        	}  	
        	}
    	  
    	
    	
    		
    	}
    	
    	catch(Exception e) {

            throw new RuntimeException("error occurred", e);
    	}
    	return response;
    }
    
    
    /**
     * Filters transactions that fall within the last three months.
     * @param transactions the list of customer transactions
     * @return filtered list of transactions within the last three months
     */
    public List<CustomerTransactions> getTransactionsDuring3MonthPeriod(List<CustomerTransactions> transactions){
 	    LocalDate startDate = LocalDate.now()
	            .minusMonths(3)
	            .withDayOfMonth(1);

	    LocalDate endDate = LocalDate.now()
	            .withDayOfMonth(1)
	            .minusDays(1);
	    List<CustomerTransactions> filterTransactions = transactions
                .stream()
                .filter(txn -> !txn.getTransactionDate().isBefore(startDate)
                        && !txn.getTransactionDate().isAfter(endDate))
                .toList();
	    return filterTransactions;
    }
    
    /**
     * Calculates reward details for a customer and prepares the reward response.
     * @param transactions the customer's transactions
     * @param custId the unique customer identifier
     * @param custName the customer name
     * @return reward summary containing monthly and total rewards
     */
    public  RewardsResponse getCustomerRewardResponse(List<CustomerTransactions> transactions,Long custId,String custName) {
    		
    	 Map<YearMonth, List<CustomerTransactions>> monthlyMap =
                 transactions.stream()
                         .collect(Collectors.groupingBy(
                                 txn -> YearMonth.from(txn.getTransactionDate())));
 	 
 	 List<MonthlyRewards> monthlyRewards = monthlyMap.entrySet().stream().map(entry->
 	 {
 		 long points = entry.getValue()
                  .stream()
                  .mapToLong(txn ->calculateRewardPoints(txn.getAmount()))
                  .sum();
 		  BigDecimal amount = entry.getValue()
                   .stream()
                   .map(txn -> txn.getAmount())
                   .reduce(BigDecimal.ZERO,
                           (a, b) -> a.add(b));
 		  return new MonthlyRewards(
                   entry.getKey(),
                   points,
                   amount);
 		 
 	 }).toList();


      long totalRewards = monthlyRewards.stream()
              .mapToLong(MonthlyRewards::getPoints)
              .sum();

      BigDecimal totalAmount = transactions.stream()
              .map(txn -> txn.getAmount())
              .reduce(BigDecimal.ZERO,
                      (a, b) -> a.add(b));
   
           return   new RewardsResponse(
                      custId,
                      custName,
                      monthlyRewards,
                      totalRewards,
                      totalAmount
              );     
    }
    
    /**
     * Calculates reward points based on the transaction amount.
     * 1 point for every dollar spent over 50
     * 2 points for every dollar spent over 100
     * @param amount the transaction amount
     * @return the calculated reward points
     */
    public Long calculateRewardPoints(BigDecimal amount) { 
    	   
    	    BigDecimal hundred = new BigDecimal("100");
    	    BigDecimal fifty = new BigDecimal("50");

    	    if (amount.compareTo(hundred) > 0) {

    	        return amount.subtract(hundred)
    	                .multiply(new BigDecimal("2"))
    	                .add(new BigDecimal("50")).longValue();
    	    }

    	    if (amount.compareTo(fifty) > 0) {

    	        return amount.subtract(fifty).longValue();
    	    }

    	    return BigDecimal.ZERO.longValue();
    	}

    
}
