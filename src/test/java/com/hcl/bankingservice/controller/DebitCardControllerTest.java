package com.hcl.bankingservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hcl.bankingservice.dto.DebitCardDto;
import com.hcl.bankingservice.service.DebitCardService;
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
public class DebitCardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DebitCardService debitCardService;

    @Test
    @DisplayName("GET /api/debitCardService/v1/debitCards/1001 - Found")
    void testGetOneByIdFound() throws Exception {
        DebitCardDto mockDebitCardDto = new DebitCardDto();
        mockDebitCardDto.setCardNumber(1234);
        mockDebitCardDto.setCvv("322");
        mockDebitCardDto.setDebitCardId(1001);

        doReturn(mockDebitCardDto).when(debitCardService)
                .getOneById(1001);

        mockMvc.perform(get("/api/debitCardService/v1/debitCards/{id}", 1001))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.debitCardId", is(1001)))
                .andExpect(jsonPath("$.cardNumber", is("1234")))
                .andExpect(jsonPath("$.cvv", is("322")));
    }

    @Test
    @DisplayName("GET ALL /api/debitCardService/v1/debitCards - Found")
    void testAllSuccess() throws Exception {
        List<DebitCardDto> mockDebitCardList = Arrays.asList(new DebitCardDto(1001,1234,"322",LocalDate.now()),
                new DebitCardDto(1002,1234,"566",LocalDate.now()));


        doReturn(mockDebitCardList).when(debitCardService)
                .getAll();

        mockMvc.perform(get("/api/debitCardService/v1/debitCards"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].cardNumber", is("1234")))
                .andExpect(jsonPath("$[1].cardNumber", is("9876")));
    }

    @Test
    @DisplayName("GET /api/debitCardService/v1/debitCards/1002 - NotFound")
    void testGetOneByIdNotFound() throws Exception {
        doReturn(null).when(debitCardService)
                .getOneById(1002);

        mockMvc.perform(get("/api/debitCardService/v1/debitCards/{id}", 1002))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("POST /api/debitCardService/v1/debitCards - Success")
    void testCreateDebitCardSuccess() throws Exception {
        DebitCardDto debitCardDtoToPost = new DebitCardDto();
        debitCardDtoToPost.setCardNumber(1234);
        debitCardDtoToPost.setCvv("322");
        debitCardDtoToPost.setDebitCardId(1001);
        debitCardDtoToPost.setExpiryDate(LocalDate.now());


        DebitCardDto mockDebitCardDto = new DebitCardDto();
        mockDebitCardDto.setCardNumber(1234);
        mockDebitCardDto.setCvv("322");
        mockDebitCardDto.setDebitCardId(1001);
        mockDebitCardDto.setExpiryDate(LocalDate.now());

        doReturn(mockDebitCardDto).when(debitCardService)
                .create(any());

        mockMvc.perform(post("/api/debitCardService/v1/debitCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(debitCardDtoToPost)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cardNumber", is("1234")));

    }

    @Test
    @DisplayName("POST /api/debitCardService/v1/debitCards- Failed")
    void testCreateDebitCardFailed() throws Exception {
        doReturn(null).when(debitCardService)
                .create(any(DebitCardDto.class));

        DebitCardDto debitCardDtoToPost = new DebitCardDto();
        mockMvc.perform(post("/api/debitCardService/v1/debitCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(debitCardDtoToPost)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/debitCardService/v1/debitCards - Success")
    void testUpdateDebitCardSuccess() throws Exception {
        DebitCardDto debitCardDtoToPost = new DebitCardDto();
        debitCardDtoToPost.setCardNumber(1234);
        debitCardDtoToPost.setCvv("322");
        debitCardDtoToPost.setDebitCardId(1001);


        DebitCardDto mockDebitCardDto = new DebitCardDto();
        mockDebitCardDto.setCardNumber(1234);
        mockDebitCardDto.setCvv("322");
        mockDebitCardDto.setDebitCardId(1001);

        doReturn(mockDebitCardDto).when(debitCardService)
                .update(any(DebitCardDto.class));

        mockMvc.perform(put("/api/debitCardService/v1/debitCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(debitCardDtoToPost)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.cardNumber", is("1234")))
                .andExpect(jsonPath("$.debitCardId", is(1001)));
    }

    @Test
    @DisplayName("PUT /api/debitCardService/v1/debitCards- Failed")
    void testPutDebitCardFailed() throws Exception {
        doReturn(null).when(debitCardService)
                .update(any(DebitCardDto.class));

        DebitCardDto DebitCardDtoToPost = new DebitCardDto();
        mockMvc.perform(put("/api/debitCardService/v1/debitCards").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(DebitCardDtoToPost)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("DELETE /api/debitCardService/v1/debitCards/1001 - SUCCESS")
    void testDeleteDebitCardSuccess() throws Exception {
        DebitCardDto mockDebitCardDto = new DebitCardDto();
        mockDebitCardDto.setCardNumber(1234);
        mockDebitCardDto.setCvv("322");
        mockDebitCardDto.setDebitCardId(1001);

        doReturn(true).when(debitCardService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/debitCardService/v1/debitCards/{id}", 1001))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/debitCardService/v1/debitCards/1002 - Failed")
    void testDeleteDebitCardFail() throws Exception {
        doReturn(false).when(debitCardService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/debitCardService/v1/debitCards/{id}", 1002))
                .andExpect(status().isBadRequest());
    }

    private static String asJsonString(final DebitCardDto DebitCardDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(DebitCardDto);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}


