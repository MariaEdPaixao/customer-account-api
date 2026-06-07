package com.customeraccountapi.controllers;

import com.customeraccountapi.domain.Account;
import com.customeraccountapi.dto.AccountCreateDTO;
import com.customeraccountapi.dto.AccountResponseDTO;
import com.customeraccountapi.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO createAccount(@RequestBody @Valid AccountCreateDTO accountDTO){
        return accountService.create(accountDTO);
    }

    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable Long accountId){
        return accountService.findById(accountId);
    }

}
