package com.example.mtptask.exception;

import java.io.Serial;

public class StarNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public StarNotFoundException(String message) {
        super(message);
    }
}
