package com.hcl.bankingservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hcl.bankingservice.dto.TransactionDto;
import com.hcl.bankingservice.enums.TransactionType;
import com.hcl.bankingservice.service.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    @DisplayName("GET /api/transactionService/v1/transactions/1001 - Found")
    void testGetOneByIdFound() throws Exception {
        TransactionDto mockTransactionDto = new TransactionDto();
        mockTransactionDto.setTransactionAmount(new BigDecimal("500.00"));
        mockTransactionDto.setTransactionDate(LocalDate.now());
        mockTransactionDto.setTransactionId(1001);

        doReturn(mockTransactionDto).when(transactionService)
                .getOneById(1001);

        mockMvc.perform(get("/api/transactionService/v1/transactions/{id}", 1001))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.transactionId", is(1001)))
                .andExpect(jsonPath("$.transactionAmount", is(500.0)));
    }

    @Test
    @DisplayName("GET ALL /api/transactionService/v1/transactions - Found")
    void testAllSuccess() throws Exception {
        List<TransactionDto> mockTransactionList = Arrays.asList(new TransactionDto(1001,LocalDate.now(),new BigDecimal("500.00"), TransactionType.WITHDRAW),
                new TransactionDto(1002,LocalDate.now(),new BigDecimal("1000.00"),TransactionType.DEPOSIT));


        doReturn(mockTransactionList).when(transactionService).getAll();

        mockMvc.perform(get("/api/transactionService/v1/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].transactionAmount", is(500.0)))
                .andExpect(jsonPath("$[1].transactionAmount", is(1000.0)));
    }

    @Test
    @DisplayName("GET /api/transactionService/v1/transactions/1002 - NotFound")
    void testGetOneByIdNotFound() throws Exception {
        doReturn(null).when(transactionService)
                .getOneById(1002);

        mockMvc.perform(get("/api/transactionService/v1/transactions/{id}", 1002))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("POST /api/transactionService/v1/transactions - Success")
    void testCreateTransactionSuccess() throws Exception {
        TransactionDto transactionDtoToPost = new TransactionDto();
        transactionDtoToPost.setTransactionAmount(new BigDecimal("500.00"));
        transactionDtoToPost.setTransactionDate(LocalDate.now());
        transactionDtoToPost.setTransactionId(1001);
        transactionDtoToPost.setTransactionType(TransactionType.WITHDRAW);


        TransactionDto mockTransactionDto = new TransactionDto();
        mockTransactionDto.setTransactionAmount(new BigDecimal("500.00"));
        mockTransactionDto.setTransactionDate(LocalDate.now());
        mockTransactionDto.setTransactionId(1001);
        transactionDtoToPost.setTransactionType(TransactionType.WITHDRAW);

        doReturn(mockTransactionDto).when(transactionService)
                .create(any());

        mockMvc.perform(post("/api/transactionService/v1/transactions").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(transactionDtoToPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionAmount", is(500.0)));

    }

    @Test
    @DisplayName("POST /api/transactionService/v1/transactions- Failed")
    void testCreateTransactionFailed() throws Exception {
        doReturn(null).when(transactionService)
                .create(any(TransactionDto.class));

        TransactionDto transactionDtoToPost = new TransactionDto();
        mockMvc.perform(post("/api/transactionService/v1/transactions").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(transactionDtoToPost)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    @DisplayName("DELETE /api/transactionService/v1/transactions/1001 - SUCCESS")
    void testDeleteTransactionSuccess() throws Exception {
        TransactionDto mockTransactionDto = new TransactionDto();
        mockTransactionDto.setTransactionAmount(new BigDecimal("500.00"));
        mockTransactionDto.setTransactionDate(LocalDate.now());
        mockTransactionDto.setTransactionId(1001);

        doReturn(true).when(transactionService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/transactionService/v1/transactions/{id}", 1001))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/transactionService/v1/transactions/1002 - Failed")
    void testDeleteTransactionFail() throws Exception {
        doReturn(false).when(transactionService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/transactionService/v1/transactions/{id}", 1002))
                .andExpect(status().isBadRequest());
    }

    private static String asJsonString(final TransactionDto TransactionDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(TransactionDto);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


