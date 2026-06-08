package com.customeraccountapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(description = "Transaction creation request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TransactionCreateDTO {
    @Schema(
            description = "Account identifier",
            example = "1"
    )
    @NotNull(message = "Account Id is required")
    private Long accountId;

    @Schema(
            description = """
                    Operation type:

                    1 = PURCHASE
                    2 = INSTALLMENT PURCHASE
                    3 = WITHDRAWAL
                    4 = PAYMENT
                    """,
            example = "4"
    )
    @NotNull(message = "Operation Type is required")
    private Long operationTypeId;

    @Schema(
            description = "Transaction amount",
            example = "123.45"
    )
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0.")
    @Digits(
            integer = 19,
            fraction = 2,
            message = "Amount must have at most 2 decimal places"
    )
    private BigDecimal amount;

}
