package com.assign.Rewards;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import com.assign.Rewards.Controller.RewardsController;
import com.assign.Rewards.GlobalExceptionHandler.CustomerNotFound;
import com.assign.Rewards.Model.MonthlyRewards;
import com.assign.Rewards.Model.Transactions;
import com.assign.Rewards.Response.RewardsResponse;
import com.assign.Rewards.Service.RewardsService;
import static org.mockito.Mockito.when;


@WebMvcTest(RewardsController.class)
class RewardsControllerTest {


    @Autowired
    private MockMvc mockMvc;
    
   @MockitoBean
   private RewardsService service;
    
    @Test
    void testGetTransactionsByCustomerId() throws Exception {
  	
    	when(service.getTransactionByCustomerId(102L)).thenReturn(
    		List.of( new Transactions(2L, 102L, "Dhanvika",  new BigDecimal("75"),
    	                        LocalDate.of(2026, 1, 8)),
    	                new Transactions(2L, 102L, "Dhanvika",  new BigDecimal("130"),
    	                        LocalDate.of(2026, 5, 1))));


         mockMvc.perform(get("/api/customerId/102"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$[1].customerName").value("Dhanvika"));

    }
    @Test
    void testGetTransactionByCustomerIdException() throws Exception {
    	
    	when(service.getTransactionByCustomerId(107L))
    	.thenThrow(new CustomerNotFound("Customer not found : "+107));

                mockMvc.perform(get("/api/customerId/107"))
                        .andExpect(status().isNotFound())
                        .andExpect(jsonPath("$.message").value("Customer not found : 107"));    
    }

    @Test
    void testCustomerRewards() throws Exception
      {
    when(service.getCustomerRewards()).thenReturn(
     List.of(new RewardsResponse(102L, "Anu",
    		 List.of(new MonthlyRewards(YearMonth.parse("2026-05"), 110, new BigDecimal("130")),
    				 new MonthlyRewards(YearMonth.parse("2026-04"), 90, new BigDecimal("120"))) ,
    		 110,new BigDecimal("130")),
    		 new RewardsResponse(103L, "Siva",
    	    		 List.of(new MonthlyRewards(YearMonth.parse("2026-03"), 0, new BigDecimal("45"))) ,
    	    		 0,new BigDecimal("45"))));
    	
    	mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].customerId")
                        .value(102))
                .andExpect(jsonPath("$[0].customerName")
                        .value("Anu"));
    }
    


    @Test
    void testCustomerRewards_scenario2()
            throws Exception {

    	 when(service.getCustomerRewards()).thenReturn(
    		     List.of(new RewardsResponse(102L, "Anu",
    		    		 List.of(new MonthlyRewards(YearMonth.parse("2026-05"), 110, new BigDecimal("130")),
    		    				 new MonthlyRewards(YearMonth.parse("2026-04"), 90, new BigDecimal("120"))) ,
    		    		 200,new BigDecimal("130")),
    		    		 new RewardsResponse(103L, "Siva",
    		    	    		 List.of(new MonthlyRewards(YearMonth.parse("2026-03"), 0, new BigDecimal("45"))) ,
    		    	    		 0,new BigDecimal("45"))));
    	
        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].totalRewards").value(200))
                .andExpect(jsonPath("$[1].totalamount")
                        .value(45.00));
    }
    
    @Test
    void testGetCustomerRewardsMonthWise() throws Exception {
    	
    when(service.getCustomerRewardsMonthWise("2026-05")).thenReturn(List.of(
                 new Transactions(5L, 101L, "Dhanvika",  new BigDecimal("120"),
                         LocalDate.of(2026, 5, 1)),
                 new Transactions(6L, 102L, "Siva",  new BigDecimal("90"),
                         LocalDate.of(2026, 5, 15))
         ));

    	
    	 mockMvc.perform(get("/api/monthWise").param("date", "2026-05"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$[0].customerId").value(101));
    }

}
