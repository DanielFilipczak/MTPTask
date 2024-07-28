package com.example.mtptask.exception;

import java.io.Serial;

public class ListIsNullOrEmptyException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ListIsNullOrEmptyException(String message) {
        super(message);
    }
}
