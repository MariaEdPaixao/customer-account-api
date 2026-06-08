package com.customeraccountapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(
        name = "ErrorResponse",
        description = "Standard API error response"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponseDTO(

        @Schema(
                description = "Date and time when the error occurred",
                example = "2025-09-10T15:30:45"
        )
        LocalDateTime timestamp,

        @Schema(
                description = "HTTP status code",
                example = "400"
        )
        Integer status,

        @Schema(
                description = "HTTP status reason",
                example = "Bad Request"
        )
        String error,

        @Schema(
                description = "General error message",
                example = "Validation failed"
        )
        String message,

        @Schema(
                description = "List of validation errors. Present only for validation failures."
        )
        List<ValidationFieldError> fields

) {}