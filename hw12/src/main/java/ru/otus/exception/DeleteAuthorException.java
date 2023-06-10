package ru.otus.exception;

import lombok.Getter;

@Getter
public class DeleteAuthorException extends ResourceNotFoundException {

    private final Long id;

    public DeleteAuthorException(Long id, Throwable ex) {
        super("Delete author with id " + id + " exception", ex);
        this.id = id;
    }
}