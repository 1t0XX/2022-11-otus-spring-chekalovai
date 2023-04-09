package ru.otus.exception.service;

import lombok.Getter;

@Getter
public class DeleteGenreException extends RuntimeException {

    private final Long id;

    public DeleteGenreException(Long id, Throwable ex) {
        super("Delete genre with id " + id + " exception", ex);
        this.id = id;
    }
}