package ru.otus.exception;

import lombok.Getter;

@Getter
public class DeleteGenreException extends ResourceNotFoundException {

    private final Long id;

    public DeleteGenreException(Long id, Throwable ex) {
        super("Delete genre with id " + id + " exception", ex);
        this.id = id;
    }
}