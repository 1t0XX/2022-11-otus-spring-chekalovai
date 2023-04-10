package ru.otus.exception.service;

public class FindAllAuthorException extends RuntimeException {

    public FindAllAuthorException(Throwable ex) {
        super(ex);
    }
}