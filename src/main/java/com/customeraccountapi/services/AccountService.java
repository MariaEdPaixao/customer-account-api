package com.customeraccountapi.services;

import com.customeraccountapi.dto.AccountCreateDTO;
import com.customeraccountapi.dto.AccountResponseDTO;

public interface AccountService {
    AccountResponseDTO create(AccountCreateDTO dto);

    AccountResponseDTO  findById(Long accountId);
}
