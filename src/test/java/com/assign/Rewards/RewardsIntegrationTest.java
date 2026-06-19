package com.assign.Rewards;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.assign.Rewards.dto.MonthlyRewardResponse;
import com.assign.Rewards.dto.RewardsResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration test class for the Rewards Service.
 *
 * This class verifies the end-to-end behavior of REST endpoints
 * exposed by the Rewards application using Spring Boot's test framework and MockMvc.
 *
 * The test cases cover:
 *     Retrieving month-wise reward details
 *     Handling scenarios with no transactions
 *     Retrieving reward details for a specific customer
 *     Customer not found exception handling
 *     Request parameter validation
 *     Constraint violation handling
 *     Retrieving rewards for all customers
 *     Application context loading verification
 *
 * MockMvc is used to perform HTTP requests and validate
 */


@AutoConfigureMockMvc
@SpringBootTest
class RewardsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    ObjectMapper objectMapper;
    
    
    @Test
    void testGetCustomerTransactionsMonthWise() throws Exception {

        MvcResult mvcResult =
                mockMvc.perform(get("/api/monthWiseRewards").param("date", "2026-05"))
                        .andExpect(status().isOk())
                        .andReturn();

        String response =mvcResult.getResponse().getContentAsString();

        List<MonthlyRewardResponse> rewards =
                objectMapper.readValue(
                        response,
                        new TypeReference<List<MonthlyRewardResponse>>() {});

        assertEquals(101,rewards.get(0).getCustomerId());

        assertEquals("Anu",rewards.get(0).getCustomerName());
        
        assertEquals(3,rewards.size());
    }
    
    @Test
    void testZeroTransactionsMonthWise() throws Exception {

        MvcResult mvcResult =
                mockMvc.perform(get("/api/monthWiseRewards").param("date", "2027-05"))
                        .andExpect(status().isOk())
                        .andReturn();

        String response =mvcResult.getResponse().getContentAsString();

        List<MonthlyRewardResponse> rewards = objectMapper.readValue(response,
                        new TypeReference<List<MonthlyRewardResponse>>() {});

        assertEquals(0,rewards.size());
    }
    
    @Test
    void testGetTransactionsByCustomerId() throws Exception {

        MvcResult mvcResult = mockMvc.perform(get("/api/customerId/102"))
                        	.andExpect(status().isOk())
                        	.andReturn();

        String response =mvcResult.getResponse().getContentAsString();

        RewardsResponse tnx =objectMapper.readValue(response, RewardsResponse.class);

        assertEquals(2, tnx.getMonthlyRewards().size());
       
    }
    
    
    @Test
    void testNocustomerException() throws Exception {

    	  mockMvc.perform(get("/api/customerId/105"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.message").value("Customer not found : 105"));       
    }
    
    @Test
    void testConstraintViolationException() throws Exception {

    	  mockMvc.perform(get("/api/customerId/-11"))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message").value("getRewardsBycustomerId.id: must be greater than 0"));       
    }
    
    @Test
    void testTransactionsNotFoundForCustomer() throws Exception {

    	  mockMvc.perform(get("/api/customerId/104"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.message").value("Transactions are not found for this customer : 104"));       
    }
    
    @Test
    void testMethodArgumentNotValidException() throws Exception {

    	  mockMvc.perform(get("/api/monthWiseRewards").param("date", "23-05"))
          .andExpect(status().isBadRequest())
          .andExpect(jsonPath("$.message")
        		  .value("getCustomerRewardDetailsMonthWise.date: Date must be in yyyy-MM format"));       
    }
    
    
    @Test
    void testGetAllCustomersRewards()
            throws Exception {

        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].customerId").value(102))
                .andExpect(jsonPath("$[2].customerName").value("Siva"));
    }
    
    @Test
    void applicationContextLoads() {
        RewardsApplication.main(new String[] {});
    }

}
