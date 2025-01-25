package com.project.opportunities.exception;

public class AlreadySubscribedException extends RuntimeException {
    public AlreadySubscribedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadySubscribedException(String message) {
        super(message);
    }
}
