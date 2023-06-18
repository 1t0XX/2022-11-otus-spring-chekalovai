package ru.otus.exception;

public class FindAllBookException extends ResourceNotFoundException {

    public FindAllBookException(Throwable ex) {
        super("FindAllBookException", ex);
    }
}