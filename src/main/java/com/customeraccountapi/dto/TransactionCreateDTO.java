package com.customeraccountapi.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionCreateDTO {
    @NotNull(message = "Account Id is required")
    private Long accountId;
    @NotNull(message = "Operation Type is required")
    private Long operationTypeId;
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0.")
    @Digits(
            integer = 19,
            fraction = 2,
            message = "Amount must have at most 2 decimal places"
    )
    private BigDecimal amount;

}
