package ru.otus.exception.service;

public class InputReadingException extends RuntimeException {
    public InputReadingException(Throwable cause) {
        super("Input reading exception", cause);
    }
}