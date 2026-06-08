package com.customeraccountapi.services;

import com.customeraccountapi.domain.Account;
import com.customeraccountapi.domain.OperationType;
import com.customeraccountapi.domain.Transaction;
import com.customeraccountapi.dto.TransactionCreateDTO;
import com.customeraccountapi.dto.TransactionResponseDTO;
import com.customeraccountapi.exceptions.ResourceNotFoundException;
import com.customeraccountapi.repositories.AccountRepository;
import com.customeraccountapi.repositories.OperationTypeRepository;
import com.customeraccountapi.repositories.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private OperationTypeRepository operationTypeRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    @DisplayName("Should create transaction successfully when account and operation type exist")
    void createTransactionSuccess() {
        TransactionCreateDTO dto = new TransactionCreateDTO(1L, 4L, new BigDecimal("100.00"));

        Account account = Account.builder()
                .accountId(1L)
                .documentNumber("12345678900")
                .build();

        OperationType operationType = OperationType.builder()
                .operationTypeId(4L)
                .description("PAYMENT")
                .build();

        Transaction transaction = Transaction.builder()
                .transactionId(1L)
                .account(account)
                .operationType(operationType)
                .amount(new BigDecimal("100.00"))
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(4L)).thenReturn(Optional.of(operationType));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        TransactionResponseDTO result = transactionService.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.transactionId());
        assertEquals(1L, result.accountId());
        assertEquals(4L, result.operationTypeId());
        assertEquals(new BigDecimal("100.00"), result.amount());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when account does not exist")
    void createTransactionAccountNotFound() {
        TransactionCreateDTO dto =
                new TransactionCreateDTO(
                        1L,
                        4L,
                        new BigDecimal("100.00")
                );

        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> transactionService.create(dto)
                );

        assertEquals("Account not found", ex.getMessage());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when operation type does not exist")
    void createTransactionOperationTypeNotFound() {
        TransactionCreateDTO dto = new TransactionCreateDTO(1L, 4L, new BigDecimal("100.00"));
        Account account = Account.builder()
                .accountId(1L)
                .documentNumber("12345678900")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(4L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex =
                assertThrows(
                        ResourceNotFoundException.class,
                        () -> transactionService.create(dto)
                );

        assertEquals("Operation type not found", ex.getMessage());
    }

    @Test
    @DisplayName("Should store positive amount for payment operation")
    void shouldStorePositiveAmountForPayment() {
        TransactionCreateDTO dto = new TransactionCreateDTO(1L,4L, new BigDecimal("-100.00"));

        Account account = Account.builder()
                .accountId(1L)
                .build();

        OperationType operationType = OperationType.builder()
                .operationTypeId(4L)
                .description("PAYMENT")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(4L)).thenReturn(Optional.of(operationType));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponseDTO response = transactionService.create(dto);

        assertEquals(new BigDecimal("100.00"),response.amount());
    }

    @Test
    @DisplayName("Should store negative amount for PURCHASE operation")
    void shouldStoreNegativeAmountForPurchase() {
        TransactionCreateDTO dto = new TransactionCreateDTO(1L,1L, new BigDecimal("100.00"));

        Account account = Account.builder()
                .accountId(1L)
                .build();

        OperationType operationType = OperationType.builder()
                .operationTypeId(1L)
                .description("PURCHASE")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(1L)).thenReturn(Optional.of(operationType));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponseDTO response = transactionService.create(dto);

        assertEquals(new BigDecimal("-100.00"),response.amount());
    }

    @Test
    @DisplayName("Should store negative amount for INSTALLMENT PURCHASE operation")
    void shouldStoreNegativeAmountForInstallmentPurchase() {
        TransactionCreateDTO dto = new TransactionCreateDTO(1L,2L, new BigDecimal("100.00"));

        Account account = Account.builder()
                .accountId(1L)
                .build();

        OperationType operationType = OperationType.builder()
                .operationTypeId(2L)
                .description("INSTALLMENT PURCHASE")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(2L)).thenReturn(Optional.of(operationType));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponseDTO response = transactionService.create(dto);

        assertEquals(new BigDecimal("-100.00"),response.amount());
    }

    @Test
    @DisplayName("Should store negative amount for WITHDRAWAL operation")
    void shouldStoreNegativeAmountForWITHDRAWAL() {
        TransactionCreateDTO dto = new TransactionCreateDTO(1L,3L, new BigDecimal("100.00"));

        Account account = Account.builder()
                .accountId(1L)
                .build();

        OperationType operationType = OperationType.builder()
                .operationTypeId(3L)
                .description("WITHDRAWAL")
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(operationTypeRepository.findById(3L)).thenReturn(Optional.of(operationType));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TransactionResponseDTO response = transactionService.create(dto);

        assertEquals(new BigDecimal("-100.00"),response.amount());
    }
}