package ru.otus.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message, Throwable ex) {
        super(message, ex);
    }
}
