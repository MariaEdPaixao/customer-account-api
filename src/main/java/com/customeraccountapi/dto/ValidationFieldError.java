package com.customeraccountapi.dto;

public record ValidationFieldError(
        String field,
        String message
) {}