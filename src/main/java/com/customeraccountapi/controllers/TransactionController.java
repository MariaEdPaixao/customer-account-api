package com.customeraccountapi.controllers;

import com.customeraccountapi.dto.ErrorResponseDTO;
import com.customeraccountapi.dto.TransactionCreateDTO;
import com.customeraccountapi.dto.TransactionResponseDTO;
import com.customeraccountapi.services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Create transaction",
            description = """
                    Creates a new financial transaction.

                    Purchase and withdrawal operations are stored as negative amounts.

                    Payment operations are stored as positive amounts.
                    """
    )
    @ApiResponse(
            responseCode = "201",
            description = "Transaction created successfully",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransactionResponseDTO.class)
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
    @ApiResponse(
            responseCode = "404",
            description = "Account or operation type not found",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDTO.class),
                    examples = {
                            @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = """
                                {
                                  "timestamp": "2026-06-08T08:07:20.6079998",
                                  "status": 404,
                                  "error": "Not Found",
                                  "message": "Operation type not found"
                                }
                                """
                            )
                    }
            )
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseDTO createTransaction(@RequestBody @Valid TransactionCreateDTO createDTO){
        return transactionService.create(createDTO);
    }
}
