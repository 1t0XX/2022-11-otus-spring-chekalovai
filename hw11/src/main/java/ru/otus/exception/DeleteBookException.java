package ru.otus.exception;

import lombok.Getter;

@Getter
public class DeleteBookException extends ResourceNotFoundException {

    private final String id;

    public DeleteBookException(String id, Throwable ex) {
        super("Delete book with id " + id + " exception", ex);
        this.id = id;
    }
}