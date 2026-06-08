package com.customeraccountapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Transaction response")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TransactionResponseDTO(
        @Schema(example = "1")
        Long transactionId,
        @Schema(example = "1")
        Long accountId,
        @Schema(example = "4")
        Long operationTypeId,
        @Schema(example = "450.53")
        BigDecimal amount
) {}
