package com.customeraccountapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AccountCreateDTO {
    @NotBlank(message = "document_number is required")
    @Size(min = 11, max = 20, message = "Minimum 11 characters and maximum 20.")
    @Pattern(regexp = "^\\d+$", message = "The document should contain only numbers.")
    private String documentNumber;
}
