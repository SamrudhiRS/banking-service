package com.hcl.bankingservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hcl.bankingservice.dto.CustomerDto;
import com.hcl.bankingservice.service.CustomerService;
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
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Test
    @DisplayName("GET /api/customerService/v1/customers/1001 - Found")
    void testGetOneByIdFound() throws Exception {
        CustomerDto mockCustomerDto = new CustomerDto();
        mockCustomerDto.setCustomerId(1001);
        mockCustomerDto.setFirstName("David");
        mockCustomerDto.setLastName("Allen");
        mockCustomerDto.setEmail("abc@d.com");
        mockCustomerDto.setPhoneNumber("1234567890");
        mockCustomerDto.setDateOfBirth(LocalDate.of(2022,9,3));

        doReturn(mockCustomerDto).when(customerService)
                .getOneById(1001);

        mockMvc.perform(get("/api/customerService/v1/customers/{id}", 1001))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET ALL /api/CustomerService/v1/Customers - Found")
    void testAllSuccess() throws Exception {
        List<CustomerDto> mockCustomerList = Arrays.asList(new CustomerDto(1001,"David","Filip","abc@d.com",LocalDate.of(2002,9,7),"1234567890"),
                new CustomerDto(1002,"James","Sather","xyz@d.com",LocalDate.of(2004,9,7),"1234562890"));


        doReturn(mockCustomerList).when(customerService)
                .getAll();

        mockMvc.perform(get("/api/customerService/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].customerId", is(1001)))
                .andExpect(jsonPath("$[1].customerId", is(1002)));
    }

    @Test
    @DisplayName("GET /api/customerService/v1/customers/1002 - NotFound")
    void testGetOneByIdNotFound() throws Exception {
        doReturn(null).when(customerService)
                .getOneById(1002);

        mockMvc.perform(get("/api/customerService/v1/customers/{id}", 1002))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("POST /api/customerService/v1/customers - Success")
    void testCreateCustomerSuccess() throws Exception {
        CustomerDto customerDtoToPost = new CustomerDto();
        customerDtoToPost.setCustomerId(1001);
        customerDtoToPost.setFirstName("David");
        customerDtoToPost.setLastName("Allen");
        customerDtoToPost.setEmail("abc@d.com");
        customerDtoToPost.setPhoneNumber("1234567890");
        customerDtoToPost.setDateOfBirth(LocalDate.of(2022,9,3));



        CustomerDto mockCustomerDto = new CustomerDto();
        mockCustomerDto.setCustomerId(1001);
        customerDtoToPost.setFirstName("David");
        customerDtoToPost.setLastName("Allen");
        customerDtoToPost.setEmail("abc@d.com");
        customerDtoToPost.setPhoneNumber("1234567890");
        customerDtoToPost.setDateOfBirth(LocalDate.of(2022,9,3));

        doReturn(mockCustomerDto).when(customerService)
                .create(any());

        mockMvc.perform(post("/api/customerService/v1/customers").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(customerDtoToPost)))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("POST /api/customerService/v1/customers- Failed")
    void testCreateCustomerFailed() throws Exception {
        doReturn(null).when(customerService)
                .create(any(CustomerDto.class));

        CustomerDto customerDtoToPost = new CustomerDto();
        mockMvc.perform(post("/api/customerService/v1/customers").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(customerDtoToPost)))
                .andExpect(status().isBadRequest());
    }



    @Test
    @DisplayName("PUT /api/customerService/v1/customers - Success")
    void testUpdateCustomerSuccess() throws Exception {
        CustomerDto customerDtoToPost = new CustomerDto();
        customerDtoToPost.setCustomerId(1001);
        customerDtoToPost.setFirstName("David");
        customerDtoToPost.setLastName("Allen");
        customerDtoToPost.setEmail("abc@d.com");
        customerDtoToPost.setPhoneNumber("1234567890");
        customerDtoToPost.setDateOfBirth(LocalDate.of(2022,9,3));



        CustomerDto mockCustomerDto = new CustomerDto();
        mockCustomerDto.setCustomerId(1001);
        mockCustomerDto.setFirstName("David");
        mockCustomerDto.setLastName("Allen");
        mockCustomerDto.setEmail("abc@d.com");
        mockCustomerDto.setPhoneNumber("1234567890");
        mockCustomerDto.setDateOfBirth(LocalDate.of(2022,9,3));

        doReturn(mockCustomerDto).when(customerService)
                .update(any(CustomerDto.class));

        mockMvc.perform(put("/api/customerService/v1/customers").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(customerDtoToPost)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.customerId", is(1001)))
                .andExpect(jsonPath("$.phoneNumber", is("1234567890")));
    }

    @Test
    @DisplayName("PUT /api/customerService/v1/customers- Failed")
    void testPutCustomerFailed() throws Exception {
        doReturn(null).when(customerService)
                .update(any(CustomerDto.class));

        CustomerDto customerDtoToPost = new CustomerDto();
        mockMvc.perform(put("/api/customerService/v1/customers").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(asJsonString(customerDtoToPost)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("DELETE /api/customerService/v1/customers/1001 - SUCCESS")
    void testDeleteCustomerSuccess() throws Exception {
        CustomerDto mockCustomerDto = new CustomerDto();
        mockCustomerDto.setCustomerId(1001);
        mockCustomerDto.setFirstName("David");
        mockCustomerDto.setLastName("Allen");
        mockCustomerDto.setEmail("abc@d.com");
        mockCustomerDto.setPhoneNumber("1234567890");
        mockCustomerDto.setDateOfBirth(LocalDate.of(2022,9,3));

        doReturn(true).when(customerService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/customerService/v1/customers/{id}", 1001))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/customerService/v1/customers/1002 - Failed")
    void testDeleteCustomerFail() throws Exception {
        doReturn(false).when(customerService)
                .delete(anyLong());

        mockMvc.perform(delete("/api/customerService/v1/customers/{id}", 1002))
                .andExpect(status().isBadRequest());
    }

    private static String asJsonString(final CustomerDto CustomerDto) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(CustomerDto);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

