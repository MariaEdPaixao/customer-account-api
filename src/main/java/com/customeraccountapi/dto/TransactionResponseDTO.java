package com.customeraccountapi.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TransactionResponseDTO(
        Long transactionId,
        Long accountId,
        Long operationTypeId,
        BigDecimal amount
) {}
