package ru.otus.exception;

import lombok.Getter;

@Getter
public class DeleteGenreException extends RuntimeException {

    private final String id;

    public DeleteGenreException(String id, Throwable ex) {
        super("Delete genre with id " + id + " exception", ex);
        this.id = id;
    }
}