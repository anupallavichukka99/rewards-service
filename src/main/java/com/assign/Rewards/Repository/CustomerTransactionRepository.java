package com.assign.Rewards.Repository;

import java.time.YearMonth;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assign.Rewards.Entity.CustomerTransactions;

@Repository
public interface CustomerTransactionRepository extends JpaRepository<CustomerTransactions,Long>{
	
	@Query("""
		       SELECT t
		       FROM CustomerTransactions t
		       WHERE YEAR(t.transactionDate) = :year
		       AND MONTH(t.transactionDate) = :month
		       """)
		List<CustomerTransactions> findByMonthAndYear(
		        @Param("month") int month,
		        @Param("year") int year);

}
