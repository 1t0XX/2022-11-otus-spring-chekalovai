package ru.otus.exception;

public class FindAllAuthorException extends ResourceNotFoundException {

    public FindAllAuthorException(Throwable ex) {
        super("FindAllAuthorException",ex);
    }
}