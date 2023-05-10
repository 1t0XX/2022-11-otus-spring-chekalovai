package ru.otus.exception;

public class FindAllAuthorException extends RuntimeException {

    public FindAllAuthorException(Throwable ex) {
        super(ex);
    }
}