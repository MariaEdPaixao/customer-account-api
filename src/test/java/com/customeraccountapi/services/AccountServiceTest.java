package com.customeraccountapi.services;

import com.customeraccountapi.domain.Account;
import com.customeraccountapi.dto.AccountCreateDTO;
import com.customeraccountapi.dto.AccountResponseDTO;
import com.customeraccountapi.exceptions.ConflictException;
import com.customeraccountapi.exceptions.ResourceNotFoundException;
import com.customeraccountapi.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    @DisplayName("Should create account successfully when document does not exist")
    void createAccountSuccess() {
        AccountCreateDTO dto = new AccountCreateDTO("12345678900");

        Account savedAccount = Account.builder()
                .accountId(1L)
                .documentNumber("12345678900")
                .build();

        when(accountRepository.findAccountByDocumentNumber(dto.getDocumentNumber()))
                .thenReturn(Optional.empty());

        when(accountRepository.save(any(Account.class)))
                .thenReturn(savedAccount);

        AccountResponseDTO result = accountService.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.accountId());
        assertEquals("12345678900", result.documentNumber());

        verify(accountRepository).save(any(Account.class));
    }

    @Test
    @DisplayName("Should throw ConflictException when Account already is cadastrate")
    public void createAccountError(){
        AccountCreateDTO dto = new AccountCreateDTO("12345678900");

        Account savedAccount = Account.builder()
                .accountId(1L)
                .documentNumber("12345678900")
                .build();

        when(accountRepository.findAccountByDocumentNumber(dto.getDocumentNumber()))
                .thenReturn(Optional.of(savedAccount));

        ConflictException thrown = Assertions.assertThrows(ConflictException.class, () -> {
           accountService.create(dto);
        });

        Assertions.assertEquals("Document already exists", thrown.getMessage()) ;
        Assertions.assertEquals(HttpStatus.CONFLICT, thrown.getStatus()) ;
    }

    @Test
    @DisplayName("Should return account when account exists")
    void findByIdSuccess() {
        Account account = Account.builder()
                .accountId(1L)
                .documentNumber("12345678900")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        AccountResponseDTO result = accountService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.accountId());
        assertEquals("12345678900", result.documentNumber());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when account does not exist")
    void findByIdError() {

        when(accountRepository.findById(1L))
                .thenReturn(Optional.empty());

        ResourceNotFoundException thrown =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> accountService.findById(1L)
                );

        assertEquals("Account not found", thrown.getMessage());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus()) ;

    }
}