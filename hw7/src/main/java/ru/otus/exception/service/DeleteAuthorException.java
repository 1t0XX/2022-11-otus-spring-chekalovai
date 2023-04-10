package ru.otus.exception.service;

import lombok.Getter;

@Getter
public class DeleteAuthorException extends RuntimeException {

    private final Long id;

    public DeleteAuthorException(Long id, Throwable ex) {
        super("Delete author with id " + id + " exception", ex);
        this.id = id;
    }
}