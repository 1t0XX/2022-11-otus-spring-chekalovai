package ru.otus.exception;

public class InputReadingException extends RuntimeException {
    public InputReadingException(Throwable cause) {
        super("Input reading exception", cause);
    }
}