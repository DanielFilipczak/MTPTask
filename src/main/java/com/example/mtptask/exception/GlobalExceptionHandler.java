package com.example.mtptask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StarNotFoundException.class)
    public ResponseEntity<Object> handleStarNotFoundException(StarNotFoundException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Current Timestamp", new Date());
        body.put("Status", HttpStatus.NOT_FOUND.value());
        body.put("Error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ListIsNullOrEmptyException.class)
    public ResponseEntity<Object> handleListIsNullOrEmptyException(ListIsNullOrEmptyException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("Current Timestamp", new Date());
        body.put("Status", HttpStatus.BAD_REQUEST.value());
        body.put("Error", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}

