package ru.otus.exception.service;

import lombok.Getter;

@Getter
public class DeleteBookException extends RuntimeException {

    private final Long id;

    public DeleteBookException(Long id, Throwable ex) {
        super("Delete book with id " + id + " exception", ex);
        this.id = id;
    }
}