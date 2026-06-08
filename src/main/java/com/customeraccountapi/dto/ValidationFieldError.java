package com.customeraccountapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ValidationFieldError",
        description = "Represents a validation error for a specific field"
)
public record ValidationFieldError(
        @Schema(
                description = "Field name that failed validation",
                example = "account_id"
        )
        String field,

        @Schema(
                description = "Validation error message explaining the failure",
                example = "Account Id is required"
        )
        String message

) {}