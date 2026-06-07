package com.customeraccountapi.exceptions.handler;

import com.customeraccountapi.dto.ErrorResponseDTO;
import com.customeraccountapi.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponseDTO> handleApiException(ApiException ex) {
        HttpStatus status = ex.getStatus();

        return ResponseEntity.status(status)
                .body(new ErrorResponseDTO(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        ex.getMessage(),
                        null
                ));
    }
}