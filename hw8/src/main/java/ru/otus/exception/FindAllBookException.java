package ru.otus.exception;

public class FindAllBookException extends RuntimeException {

    public FindAllBookException(Throwable ex) {
        super(ex);
    }
}