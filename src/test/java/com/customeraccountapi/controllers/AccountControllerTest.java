package com.customeraccountapi.controllers;

import com.customeraccountapi.domain.Account;
import com.customeraccountapi.dto.AccountResponseDTO;
import com.customeraccountapi.exceptions.ConflictException;
import com.customeraccountapi.exceptions.ResourceNotFoundException;
import com.customeraccountapi.services.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    @DisplayName("POST /accounts - should create account successfully when request is valid")
    void shouldCreateAccountSuccessfully() throws Exception {
        AccountResponseDTO response =
                new AccountResponseDTO(1L, "12345678900");

        when(accountService.create(any()))
                .thenReturn(response);

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "document_number": "12345678900"
                            }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.account_id").value(1L))
                .andExpect(jsonPath("$.document_number").value("12345678900"));
    }

    @Test
    @DisplayName("POST /accounts - should return 400 Bad Request when request body is invalid")
    void shouldReturnBadRequestWhenInvalidBody() throws Exception {
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "documentNumber": ""
                }
            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    @DisplayName("POST /accounts - should return 409 Conflict when document already exists")
    void shouldReturnConflictWhenDocumentAlreadyExists() throws Exception {
        when(accountService.create(any()))
                .thenThrow(new ConflictException("Document already exists"));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "document_number": "12345678900"
                }
            """))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message")
                        .value("Document already exists"));
    }

    @Test
    @DisplayName("GET /accounts/{id} - should return account when id exists")
    void shouldReturnAccountById() throws Exception {
        AccountResponseDTO response =
                new AccountResponseDTO(1L, "12345678900");

        when(accountService.findById(1L))
                .thenReturn(response);

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.account_id").value(1L))
                .andExpect(jsonPath("$.document_number").value("12345678900"));
    }

    @Test
    @DisplayName("GET /accounts/{id} - should return 404 Not Found when account does not exist")
    void shouldReturnNotFoundWhenAccountDoesNotExist() throws Exception {

        when(accountService.findById(1L))
                .thenThrow(new ResourceNotFoundException("Account not found"));

        mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("Account not found"));
    }
}