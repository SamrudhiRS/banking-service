package com.hcl.bankingservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hcl.bankingservice.dto.AccountDto;
import com.hcl.bankingservice.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.hcl.bankingservice.enums.AccountType.CURRENT;
import static com.hcl.bankingservice.enums.AccountType.SAVING;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AccountService accountService;


    @InjectMocks
    private AccountController doctorController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("GET /api/accountService/v1/accounts/1001 - Found")
    void testGetOneByIdFound() throws Exception {
        AccountDto mockAccountDto = new AccountDto();
        mockAccountDto.setAccountId(1001);
        // 2222222222222222222222222222222222222222222222
        mockAccountDto.setAccountNumber( );
        mockAccountDto.setCustomerId(1001);
        mockAccountDto.setDateOfOpening(LocalDate.now());

        doReturn(mockAccountDto).when(accountService)
                .getOneById(1001);

        mockMvc.perform(get("/api/accountService/v1/accounts/{id}", 1001))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.accountId", is(1001)))
                .andExpect(jsonPath("$.accountNumber", is("123456")))
                .andExpect(jsonPath("$.customerId", is(1001)));
    }



    @Test
    @DisplayName("GET /api/accountService/v1/accounts/1002 - NotFound")
    void testGetOneByIdNotFound() throws Exception {
        doReturn(null).when(accountService)
                .getOneById(1002);

        mockMvc.perform(get("/api/accountService/v1/accounts/{id}", 1002))
                .andExpect(status().isNotFound());
    }


//    @Test
//    @DisplayName("POST /api/accountService/v1/accounts - Success")
//    void testCreateAccountSuccess() throws Exception {
//        AccountDto accountDtoToPost = new AccountDto();
//        accountDtoToPost.setAccountNumber("12345678");
//        accountDtoToPost.setAccountType(SAVING);
//        accountDtoToPost.setDateOfOpening(LocalDate.now());
//
//
//        AccountDto mockAccountDto = new AccountDto();
//        mockAccountDto.setAccountNumber("123456");
//        mockAccountDto.setAccountId(1002);
//        mockAccountDto.setAccountType(SAVING);
//        accountDtoToPost.setDateOfOpening(LocalDate.now());
//        doReturn(mockAccountDto).when(accountService)
//                .create(any());
//
//        mockMvc.perform(post("/api/accountService/v1/accounts").contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(asJsonString(accountDtoToPost)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.accountNumber", is("123456")))
//                .andExpect(jsonPath("$.accountId", is(1002)))
//                .andExpect(jsonPath("$.accountType", is("SAVING")));
//
//    }
//
//    @Test
//    @DisplayName("POST /api/accountService/v1/accounts- Failed")
//    void testCreateAccountFailed() throws Exception {
//        doReturn(null).when(accountService)
//                .create(any(AccountDto.class));
//
//        AccountDto accountDtoToPost = new AccountDto();
//        mockMvc.perform(post("/api/accountService/v1/accounts").contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(asJsonString(accountDtoToPost)))
//                .andExpect(status().isBadRequest());
//    }


//    @Test
//    @DisplayName("PUT /api/accountService/v1/accounts - Success")
//    void testUpdateAccountSuccess() throws Exception {
//        AccountDto accountDtoToPost = new AccountDto();
//        accountDtoToPost.setAccountNumber("12345678");
//        accountDtoToPost.setAccountType(SAVING);
//        accountDtoToPost.setDateOfOpening(LocalDate.now());
//
//
//        AccountDto mockAccountDto = new AccountDto();
//        mockAccountDto.setAccountNumber("123456");
//        mockAccountDto.setAccountId(1002);
//        mockAccountDto.setAccountType(SAVING);
//        accountDtoToPost.setDateOfOpening(LocalDate.now());
//        doReturn(mockAccountDto).when(accountService)
//                .update(any(AccountDto.class));
//
//        mockMvc.perform(put("/api/accountService/v1/accounts").contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(asJsonString(accountDtoToPost)))
//                .andExpect(status().isAccepted())
//                .andExpect(jsonPath("$.accountNumber", is("123456")))
//                .andExpect(jsonPath("$.accountId", is(1002)))
//                .andExpect(jsonPath("$.accountType", is("SAVING")));
//    }
//
//    @Test
//    @DisplayName("PUT /api/accountService/v1/accounts- Failed")
//    void testPutAccountFailed() throws Exception {
//        doReturn(null).when(accountService)
//                .update(any(AccountDto.class));
//
//        AccountDto accountDtoToPost = new AccountDto();
//        mockMvc.perform(put("/api/accountService/v1/accounts").contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .content(asJsonString(accountDtoToPost)))
//                .andExpect(status().isUnprocessableEntity());
//    }

    @Test
    @DisplayName("DELETE /api/accountService/v1/accounts/1001 - SUCCESS")
    void testDeleteAccountSuccess() throws Exception {
        AccountDto mockAccountDto = new AccountDto();
        mockAccountDto.setAccountId(1001);
        mockAccountDto.setAccountNumber(123456);
        mockAccountDto.setCustomerId(1001);
        mockAccountDto.setDateOfOpening(LocalDate.now());

        doReturn(true).when(accountService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/accountService/v1/accounts/{id}", 1001))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/accountService/v1/accounts/1002 - Failed")
    void testDeleteAccountFail() throws Exception {
        // setup the mock AccountService with all needed details
        doReturn(false).when(accountService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/accountService/v1/accounts/{id}", 1002))
                .andExpect(status().isBadRequest());
    }

    private static String asJsonString(final AccountDto AccountDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(AccountDto);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

