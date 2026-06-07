package com.customeraccountapi.controllers;

import com.customeraccountapi.dto.TransactionCreateDTO;
import com.customeraccountapi.dto.TransactionResponseDTO;
import com.customeraccountapi.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDTO createTransaction(@RequestBody @Valid TransactionCreateDTO createDTO){
        return transactionService.create(createDTO);
    }
}
