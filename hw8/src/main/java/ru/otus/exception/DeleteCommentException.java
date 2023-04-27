package ru.otus.exception;

import lombok.Getter;

@Getter
public class DeleteCommentException extends RuntimeException {

    private final String id;

    public DeleteCommentException(String id, Throwable ex) {
        super("Delete comment with id " + id + " exception", ex);
        this.id = id;
    }
}