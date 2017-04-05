package com.pawmot.hajsback.api.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class HttpStatusException extends RuntimeException {
    @Getter
    private HttpStatus httpStatus;

    public HttpStatusException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
