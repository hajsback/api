package com.pawmot.hajsback.api.exceptionHandling;

import com.pawmot.hajsback.api.exceptions.HttpStatusException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate key")
    @ExceptionHandler({DuplicateKeyException.class})
    public void duplicateKey() { }

    @ExceptionHandler({HttpStatusException.class})
    public ResponseEntity<Void> httpStatusException(HttpStatusException exception) {
        return ResponseEntity.status(exception.getHttpStatus()).build();
    }
}
