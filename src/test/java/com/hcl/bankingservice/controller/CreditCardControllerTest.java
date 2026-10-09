package com.hcl.bankingservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hcl.bankingservice.dto.CreditCardDto;
import com.hcl.bankingservice.service.CreditCardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
public class CreditCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardService creditCardService;

    @Test
    @DisplayName("GET /api/creditCardService/v1/creditCards/1001 - Found")
    void testGetOneByIdFound() throws Exception {
        CreditCardDto mockCreditCardDto = new CreditCardDto();
        mockCreditCardDto.setCardNumber(12345);
        mockCreditCardDto.setCvv("322");
        mockCreditCardDto.setCreditCardId(1001);

        doReturn(mockCreditCardDto).when(creditCardService)
                .getOneById(1001);

        mockMvc.perform(get("/api/creditCardService/v1/creditCards/{id}", 1001))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.creditCardId", is(1001)))
                .andExpect(jsonPath("$.cardNumber", is("1234")))
                .andExpect(jsonPath("$.cvv", is("322")));
    }

    @Test
    @DisplayName("GET ALL /api/creditCardService/v1/creditCards - Found")
    void testAllSuccess() throws Exception {
        List<CreditCardDto> mockCreditCardList = Arrays.asList(new CreditCardDto(1001,1234,"322",LocalDate.now()),
                new CreditCardDto(1002,1234,"566",LocalDate.now()));


        doReturn(mockCreditCardList).when(creditCardService)
                .getAll();

        mockMvc.perform(get("/api/creditCardService/v1/creditCards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].cardNumber", is("1234")))
                .andExpect(jsonPath("$[1].cardNumber", is("9876")));
    }

    @Test
    @DisplayName("GET /api/creditCardService/v1/creditCards/1002 - NotFound")
    void testGetOneByIdNotFound() throws Exception {
        doReturn(null).when(creditCardService)
                .getOneById(1002);

        mockMvc.perform(get("/api/creditCardService/v1/creditCards/{id}", 1002))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("POST /api/creditCardService/v1/creditCards - Success")
    void testCreateCreditCardSuccess() throws Exception {
        CreditCardDto creditCardDtoToPost = new CreditCardDto();
        creditCardDtoToPost.setCardNumber(12345);
        creditCardDtoToPost.setCvv("322");
        creditCardDtoToPost.setCreditCardId(1001);
        creditCardDtoToPost.setExpiryDate(LocalDate.now());


        CreditCardDto mockCreditCardDto = new CreditCardDto();
        mockCreditCardDto.setCardNumber(12345);
        mockCreditCardDto.setCvv("322");
        mockCreditCardDto.setCreditCardId(1001);
        mockCreditCardDto.setExpiryDate(LocalDate.now());

        doReturn(mockCreditCardDto).when(creditCardService)
                .create(any());

        mockMvc.perform(post("/api/creditCardService/v1/creditCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(creditCardDtoToPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardNumber", is("1234")));

    }

    @Test
    @DisplayName("POST /api/creditCardService/v1/creditCards- Failed")
    void testCreateCreditCardFailed() throws Exception {
        doReturn(null).when(creditCardService)
                .create(any(CreditCardDto.class));

        CreditCardDto creditCardDtoToPost = new CreditCardDto();
        mockMvc.perform(post("/api/creditCardService/v1/creditCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(creditCardDtoToPost)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/creditCardService/v1/creditCards - Success")
    void testUpdateCreditCardSuccess() throws Exception {
        CreditCardDto creditCardDtoToPost = new CreditCardDto();
        creditCardDtoToPost.setCardNumber(1234);
        creditCardDtoToPost.setCvv("322");
        creditCardDtoToPost.setCreditCardId(1001);


        CreditCardDto mockCreditCardDto = new CreditCardDto();
        mockCreditCardDto.setCardNumber(1234);
        mockCreditCardDto.setCvv("322");
        mockCreditCardDto.setCreditCardId(1001);

        doReturn(mockCreditCardDto).when(creditCardService)
                .update(any(CreditCardDto.class));

        mockMvc.perform(put("/api/creditCardService/v1/creditCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(creditCardDtoToPost)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.cardNumber", is("1234")))
                .andExpect(jsonPath("$.creditCardId", is(1001)));
    }

    @Test
    @DisplayName("PUT /api/creditCardService/v1/creditCards- Failed")
    void testPutCreditCardFailed() throws Exception {
        doReturn(null).when(creditCardService)
                .update(any(CreditCardDto.class));

        CreditCardDto creditCardDtoToPost = new CreditCardDto();
        mockMvc.perform(put("/api/creditCardService/v1/creditCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(creditCardDtoToPost)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("DELETE /api/creditCardService/v1/creditCards/1001 - SUCCESS")
    void testDeleteCreditCardSuccess() throws Exception {
        CreditCardDto mockCreditCardDto = new CreditCardDto();
        mockCreditCardDto.setCardNumber(1234);
        mockCreditCardDto.setCvv("322");
        mockCreditCardDto.setCreditCardId(1001);

        doReturn(true).when(creditCardService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/creditCardService/v1/creditCards/{id}", 1001))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/creditCardService/v1/creditCards/1002 - Failed")
    void testDeleteCreditCardFail() throws Exception {
        doReturn(false).when(creditCardService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/creditCardService/v1/creditCards/{id}", 1002))
                .andExpect(status().isBadRequest());
    }

    private static String asJsonString(final CreditCardDto CreditCardDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(CreditCardDto);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


