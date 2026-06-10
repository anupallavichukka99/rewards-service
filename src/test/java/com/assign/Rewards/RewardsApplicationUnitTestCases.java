package com.assign.Rewards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.assign.Rewards.GlobalExceptionHandler.CustomerNotFound;
import com.assign.Rewards.GlobalExceptionHandler.TransactionsNotFound;
import com.assign.Rewards.Model.Transactions;
import com.assign.Rewards.Repository.RewardsRepository;
import com.assign.Rewards.Response.RewardsResponse;
import com.assign.Rewards.Service.RewardsService;



@ExtendWith(MockitoExtension.class)
class RewardsApplicationUnitTestCases {


    @Mock
    private RewardsRepository repository;

    @InjectMocks
    private RewardsService rewardService;

    @Test
    void testCustomerTransactionsByCustomerId() {

        when(repository.findBycustomerId(102L))
        .thenReturn(Optional.of(List.of(
        		new Transactions(2L,102L,"Dhanvika",new BigDecimal("130"), LocalDate.of(2025, 5, 10)),
        		new Transactions(5L,102L,"Dhanvika",new BigDecimal("180"), LocalDate.of(2025, 4, 10))
        		)));
        

        List<Transactions> result = rewardService.getTransactionByCustomerId(102L);
       

        assertEquals(2, result.size());
        assertEquals("Dhanvika", result.get(0).getCustomerName());

      
    }

    @Test
    void testTotalPointsCalculation() {
    	
    assertEquals(rewardService.calculateRewardPoints(new BigDecimal("130")),110);
       
    }

  
    @Test
    void testZeroAmount() {

        when(repository.getAllTransactions()).thenReturn(List.of(
                new Transactions(1L, 101L, "Anu", new BigDecimal("0"),
                        LocalDate.of(2025, 5, 10))
        ));

        List<RewardsResponse> result = rewardService.getCustomerRewards();

        assertEquals(result.size(),0);
    }
    
    @Test
    void testRewardException() {

        when(repository.getAllTransactions()).thenReturn(Collections.emptyList());

        assertThrows(TransactionsNotFound.class, () -> {rewardService.getCustomerRewards(); });
    }
}



