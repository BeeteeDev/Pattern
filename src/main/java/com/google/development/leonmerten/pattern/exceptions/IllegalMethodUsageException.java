package com.google.development.leonmerten.pattern.exceptions;

public class IllegalMethodUsageException extends Exception {
    public IllegalMethodUsageException(String message) {
        super(message);
    }

    public IllegalMethodUsageException() {
        super();
    }
}
