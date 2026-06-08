package com.customeraccountapi.services;

import com.customeraccountapi.domain.Account;
import com.customeraccountapi.domain.OperationType;
import com.customeraccountapi.domain.Transaction;
import com.customeraccountapi.domain.enums.OperationTypeEnum;
import com.customeraccountapi.dto.TransactionCreateDTO;
import com.customeraccountapi.dto.TransactionResponseDTO;
import com.customeraccountapi.exceptions.ResourceNotFoundException;
import com.customeraccountapi.repositories.AccountRepository;
import com.customeraccountapi.repositories.OperationTypeRepository;
import com.customeraccountapi.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService{

    final TransactionRepository transactionRepository;
    final AccountRepository accountRepository;
    final OperationTypeRepository operationTypeRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, AccountRepository accountRepository, OperationTypeRepository operationTypeRepository){
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.operationTypeRepository = operationTypeRepository;
    }

    @Override
    @Transactional
    public TransactionResponseDTO create(TransactionCreateDTO createDTO){
        Account account = this.findAccount(createDTO.getAccountId());
        OperationType operationType = this.findOperationType(createDTO.getOperationTypeId());
        BigDecimal amount = normalizeAmount(createDTO.getOperationTypeId(), createDTO.getAmount());
        Transaction transaction = buildTransaction(account, operationType, amount);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction);
    }

    private Account findAccount(Long accountId){
        return accountRepository.findById(accountId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Account not found"));
    }
    private OperationType findOperationType(Long operationType){
        return operationTypeRepository.findById(operationType)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Operation type not found"));
    }
    private Transaction buildTransaction(Account account, OperationType operationType, BigDecimal amount){
        return  Transaction.builder()
                .account(account)
                .operationType(operationType)
                .amount(amount)
                .eventDate(LocalDateTime.now())
                .build();
    }
    private TransactionResponseDTO mapToResponse(Transaction transaction){
        return new TransactionResponseDTO(
                transaction.getTransactionId(),
                transaction.getAccount().getAccountId(),
                transaction.getOperationType().getOperationTypeId(),
                transaction.getAmount()
        );
    }
    private BigDecimal normalizeAmount(Long operationTypeId, BigDecimal amount){
        if(operationTypeId.equals(OperationTypeEnum.PAYMENT.getId())){
            return amount.abs();
        }

        return amount.abs().negate();
    }

}
