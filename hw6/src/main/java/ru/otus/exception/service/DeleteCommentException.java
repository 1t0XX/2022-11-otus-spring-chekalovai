package ru.otus.exception.service;

import lombok.Getter;

@Getter
public class DeleteCommentException extends RuntimeException {

    private final Long id;

    public DeleteCommentException(Long id, Throwable ex) {
        super("Delete comment with id " + id + " exception", ex);
        this.id = id;
    }

}
