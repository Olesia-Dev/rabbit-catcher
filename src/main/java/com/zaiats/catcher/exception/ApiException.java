package com.zaiats.catcher.exception;

import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ApiException {

    private final Instant timestamp;
    private final HttpStatus httpStatus;
    private final String message;

    public ApiException(Instant timestamp, HttpStatus httpStatus, String message) {
        this.timestamp = timestamp;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

}
