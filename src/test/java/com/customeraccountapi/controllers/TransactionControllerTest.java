package com.customeraccountapi.controllers;

import com.customeraccountapi.dto.TransactionResponseDTO;
import com.customeraccountapi.exceptions.ResourceNotFoundException;
import com.customeraccountapi.services.TransactionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    @DisplayName("POST /transactions - should create transaction successfully and return status 201")
    void shouldCreateTransactionSuccessfully() throws Exception {
        TransactionResponseDTO response = new TransactionResponseDTO(1L,1L,4L, new BigDecimal("100.00"));

        when(transactionService.create(any())).thenReturn(response);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                  "account_id": 1,
                  "operation_type_id": 4,
                  "amount": 100.00
                }
            """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.transaction_id").value(1))
                .andExpect(jsonPath("$.account_id").value(1))
                .andExpect(jsonPath("$.operation_type_id").value(4))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    @DisplayName("POST /transactions - should return 400 when account_id is not provided")
    void shouldReturnBadRequestWhenAccountIdIsNull() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "operation_type_id": 4,
                                  "amount": 100.00
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.fields[0].field").value("account_id"))
                .andExpect(jsonPath("$.fields[0].message").value("Account Id is required"));
    }

    @Test
    @DisplayName("POST /transactions - should return 400 when amount is null")
    void shouldReturnBadRequestWhenAmountIsNull() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "account_id": 1,
                                  "operation_type_id": 4
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields[0].field").value("amount"))
                .andExpect(jsonPath("$.fields[0].message").value("Amount is required"));
    }

    @Test
    @DisplayName("POST /transactions - should return 400 when amount is negative")
    void shouldReturnBadRequestWhenAmountIsNegative() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "account_id": 1,
                                  "operation_type_id": 4,
                                  "amount": -50
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields[0].field").value("amount"))
                .andExpect(jsonPath("$.fields[0].message").value("Amount must be greater than 0."));
    }

    @Test
    @DisplayName("POST /transactions - should return 400 when amount has more than 2 decimal places")
    void shouldReturnBadRequestWhenAmountHasMoreThanTwoDecimalPlaces() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "account_id": 1,
                                  "operation_type_id": 4,
                                  "amount": 100.536
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields[0].field").value("amount"))
                .andExpect(jsonPath("$.fields[0].message").value("Amount must have at most 2 decimal places"));
    }

    @Test
    @DisplayName("POST /transactions - should return 400 when operation_type_id is not provided")
    void shouldReturnBadRequestWhenOperationTypeIsNull() throws Exception {
        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "account_id": 1,
                                  "amount": 100.00
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields[0].field").value("operation_type_id"))
                .andExpect(jsonPath("$.fields[0].message").value("Operation Type is required"));
    }

    @Test
    @DisplayName("POST /transactions - should return 404 when account does not exist")
    void shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {
        when(transactionService.create(any())).thenThrow(new ResourceNotFoundException("Account not found"));

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "account_id": 999,
                          "operation_type_id": 4,
                          "amount": 100.00
                        }
                        """))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /transactions - should return 404 when operation type does not exist")
    void shouldReturnNotFoundWhenOperationTypeDoesNotExist() throws Exception {
        when(transactionService.create(any())).thenThrow(new ResourceNotFoundException("Operation type not found"));

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "account_id": 1,
                          "operation_type_id": 999,
                          "amount": 100.00
                        }
                        """))
                .andExpect(status().isNotFound());
    }
}