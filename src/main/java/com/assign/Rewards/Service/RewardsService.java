package com.assign.Rewards.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.assign.Rewards.GlobalExceptionHandler.CustomerNotFound;
import com.assign.Rewards.GlobalExceptionHandler.TransactionsNotFound;
import com.assign.Rewards.Model.MonthlyRewards;
import com.assign.Rewards.Model.Transactions;
import com.assign.Rewards.Repository.RewardsRepository;
import com.assign.Rewards.Response.RewardsResponse;


/**
 * Service class responsible for managing customer rewards and transaction-related operations.
 *
 * Provides functionality to:
 * - Fetch all transactions for a specific customer
 * - customers transactions and rewards filtered by month.
 * -  Calculates and returns reward summary for all customers.
 */

@Service
public class RewardsService {

	@Autowired
	private final RewardsRepository repository;

    public RewardsService(RewardsRepository repository) {
        this.repository = repository;
    }
    
    /**
     * Retrieves all transactions for a specific customer.
     * @param id the unique identifier of the customer
     * @return list of transactions belonging to the customer
     * @throws CustomerNotFound if no transactions are found for the given customer ID
     */
      public List<Transactions> getTransactionByCustomerId(Long id) {
    	return repository.findBycustomerId(id)
    	        .filter(list -> !list.isEmpty())
    	        .orElseThrow(() -> new CustomerNotFound("Customer not found : " + id));
    }

      
    /**
     * Retrieves customers transactions and rewards filtered by month.
     * @param date the year/month reference in string format (e.g., "2026-01")
     * @return list of transactions for all customers for the given month
     */  
    public List<Transactions> getCustomerRewardsMonthWise(String date) { 	  
    	List<Transactions>list =this.repository.findAll();
    	YearMonth inputMonth = YearMonth.parse(date);
    	List<Transactions> result =list.stream()
    	           				.filter(t -> YearMonth.from(t.getTransactionDate()).equals(inputMonth))
    	           				.toList();
    	return result;	
    }
   
    
    /**
     * Calculates and returns reward summary for all customers.
     * @return list of reward responses containing customer transactions details and reward details
     */
       public List<RewardsResponse> getCustomerRewards() {

        List<Transactions> transactions =repository.getAllTransactions();
        
        if(transactions.size()==0) {
        	throw new TransactionsNotFound("No records are there ");
        }

        Map<Long, List<Transactions>> customerTransactions =transactions.stream().collect(Collectors.groupingBy(t->t.getCustomerId()));

        List<RewardsResponse> response =new ArrayList<>();

        for (Map.Entry<Long, List<Transactions>> entry: customerTransactions.entrySet()) {

            Long customerId = entry.getKey();

            List<Transactions> totalCustomerTxns =entry.getValue();

            String customerName =totalCustomerTxns.get(0).getCustomerName();

            LocalDate sd=LocalDate.now().minusMonths(3).withDayOfMonth(1);
            
            LocalDate ed=LocalDate.now().withDayOfMonth(1).minusDays(1);
            
            List<Transactions> customerTxns= totalCustomerTxns.stream()
			.filter(h-> !h.getTransactionDate().isBefore(sd) && !h.getTransactionDate().isAfter(ed)).toList();
                         
            Map<YearMonth, List<Transactions>> monthlyRewards = customerTxns.stream()
            			      									.collect(Collectors.groupingBy(txn -> YearMonth.from( txn.getTransactionDate())));
           
            List<MonthlyRewards> monthlyRewardList = monthlyRewards.entrySet().stream().map(p -> 
          {
            BigDecimal totalAmount =p.getValue() .stream().map(k->k.getAmount())
                                                .reduce(BigDecimal.ZERO,(a,b) -> a.add(b));

            long totalPoints =p.getValue().stream()
        		   .mapToLong(txn ->calculateRewardPoints(txn.getAmount())).sum();
                                
            return new MonthlyRewards( p.getKey(),totalPoints,totalAmount);
          }).toList();
           
            long total =monthlyRewardList.stream()
                            .mapToLong(u->u.getPoints()).sum();
            
            BigDecimal totalAmount = customerTxns.stream()
            	    .map(txn -> txn.getAmount())
            	    .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
            
            response.add( new RewardsResponse( customerId,customerName, monthlyRewardList,total,totalAmount ));
        }

      
       return response.stream().filter(b->b.getMonthlyRewards().size()>0).collect(Collectors.toList());
    }

       
       /**
        * Calculates reward points based on the transaction amount.
        * Reward points are computed using business rules defined for customer rewards.
        * @return calculated reward points as a long value
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
