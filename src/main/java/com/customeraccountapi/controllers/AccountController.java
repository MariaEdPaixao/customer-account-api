package com.customeraccountapi.controllers;

import com.customeraccountapi.dto.AccountCreateDTO;
import com.customeraccountapi.dto.AccountResponseDTO;
import com.customeraccountapi.dto.ErrorResponseDTO;
import com.customeraccountapi.services.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Create account",
            description = "Creates a new account using a unique document number"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Account created successfully"
    )
    @ApiResponse(
            responseCode = "409",
            description = "Document already exists",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = {
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                             	"timestamp": "2026-06-08T08:11:34.3355559",
                                             	"status": 409,
                                             	"error": "Conflict",
                                             	"message": "Document already exists"
                                             }
                                    """
                            )
                    }
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponseDTO.class)
    )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO createAccount(@RequestBody @Valid AccountCreateDTO accountDTO){
        return accountService.create(accountDTO);
    }

    @Operation(
            summary = "Get account by id",
            description = "Returns an account by its identifier"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account found"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Account not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = {
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                            {
                                                "timestamp": "2026-06-08T08:13:50.9900143",
                                                "status": 404,
                                                "error": "Not Found",
                                                "message": "Account not found"
                                            }
                                    """
                            )
                    }
            )
    )
    @GetMapping("/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public AccountResponseDTO  getAccount(@PathVariable Long accountId){
        return accountService.findById(accountId);
    }

}
