package ru.otus.exception;

import lombok.Getter;

@Getter
public class DeleteBookException extends ResourceNotFoundException {

    private final Long id;

    public DeleteBookException(Long id, Throwable ex) {
        super("Delete book with id " + id + " exception", ex);
        this.id = id;
    }
}