package com.customeraccountapi.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OperationTypeEnum {

    PURCHASE(1L),
    INSTALLMENT_PURCHASE(2L),
    WITHDRAWAL(3L),
    PAYMENT(4L);

    private final Long id;
}