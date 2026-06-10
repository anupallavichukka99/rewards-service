package com.assign.Rewards;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.assign.Rewards.Model.Transactions;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@SpringBootTest
class RewardsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    ObjectMapper objectMapper;
    
    
    @Test
    void testGetCustomerTransactionssMonthWise() throws Exception {

        MvcResult mvcResult =
                mockMvc.perform(get("/api/monthWise").param("date", "2026-05"))
                        .andExpect(status().isOk())
                        .andReturn();

        String response =
                mvcResult.getResponse().getContentAsString();

        List<Transactions> rewards =
                objectMapper.readValue(
                        response,
                        new TypeReference<List<Transactions>>() {
                        });

        assertEquals(102,
                rewards.get(0).getCustomerId());

        assertEquals("Dhanvika",
                rewards.get(0).getCustomerName());
    }
    
    @Test
    void testZeroTransactionsMonthWise() throws Exception {

        MvcResult mvcResult =
                mockMvc.perform(get("/api/monthWise").param("date", "2027-05"))
                        .andExpect(status().isOk())
                        .andReturn();

        String response =
                mvcResult.getResponse().getContentAsString();

        List<Transactions> rewards =
                objectMapper.readValue(
                        response,
                        new TypeReference<List<Transactions>>() {
                        });

        assertEquals(0,rewards.size());
    }
    
    @Test
    void testGetTransactionsByCustomerId() throws Exception {

        MvcResult mvcResult =
                mockMvc.perform(get("/api/customerId/102"))
                        .andExpect(status().isOk())
                        .andReturn();

        String response =
                mvcResult.getResponse().getContentAsString();

        List<Transactions> tnx =
                objectMapper.readValue(
                        response,
                        new TypeReference<List<Transactions>>() {
                        });

        assertEquals(3, tnx.size());

       
    }
    
    
    @Test
    void testNocustomerIdException() throws Exception {

    	  mockMvc.perform(get("/api/customerId/104"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.message").value("Customer not found : 104"));       
    }
    
    @Test
    void shouldReturnCustomerRewards()
            throws Exception {

        mockMvc.perform(get("/api/rewards"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].customerId")
                        .value(102))
                .andExpect(jsonPath("$[2].customerName")
                        .value("Siva"));
    }
    
    @Test
    void applicationContextLoads() {
        RewardsApplication.main(new String[] {});
    }

}
