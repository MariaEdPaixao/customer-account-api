package com.customeraccountapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "Account creation request")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountCreateDTO {
    @Schema(
            description = "Document number of account",
            example = "12345678900"
    )
    @NotBlank(message = "document_number is required")
    @Size(min = 11, max = 20, message = "Minimum 11 characters and maximum 20.")
    @Pattern(regexp = "^\\d+$", message = "The document should contain only numbers.")
    private String documentNumber;
}
