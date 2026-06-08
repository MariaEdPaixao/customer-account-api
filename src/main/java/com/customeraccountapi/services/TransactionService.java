package com.customeraccountapi.services;

import com.customeraccountapi.dto.TransactionCreateDTO;
import com.customeraccountapi.dto.TransactionResponseDTO;

public interface TransactionService {
    TransactionResponseDTO create(TransactionCreateDTO createDTO);
}
