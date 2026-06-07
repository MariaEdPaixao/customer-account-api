package com.customeraccountapi.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "operation_types")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OperationType {
    @Id
    @Column(name = "operation_type_id", nullable = false, unique = true)
    private Long operationTypeId;

    @NotBlank
    @Size(min = 3, max = 100, message = "Minimum 3 characters and maximum 100.")
    private String description;

}
