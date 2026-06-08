package com.customeraccountapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "Account response")
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AccountResponseDTO(
        @Schema(
                description = "Account identifier",
                example = "1"
        )
        Long accountId,
        @Schema(
                example = "12345678900"
        )
        String documentNumber
) {}