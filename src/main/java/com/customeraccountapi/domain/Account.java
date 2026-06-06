package com.customeraccountapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false, unique = true)
    private Long accountId;

    @Column(name = "document_number", nullable = false, unique = true, length = 20)
    @NotBlank
    @Size(min = 11, max = 20, message = "Minimum 11 characters and maximum 20.")
    @Pattern(regexp = "^\\d+$", message = "The document should contain only numbers.")
    private String documentNumber;
}
