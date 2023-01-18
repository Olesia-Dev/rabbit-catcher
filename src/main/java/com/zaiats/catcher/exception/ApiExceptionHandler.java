package com.zaiats.catcher.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiException apiException = new ApiException(Instant.now(),
                HttpStatus.NOT_FOUND,
                e.getMessage());
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
    
}
