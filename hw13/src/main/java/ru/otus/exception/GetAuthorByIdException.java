package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetAuthorByIdException extends ResourceNotFoundException {

    private final Long authorId;

    public GetAuthorByIdException(Long authorId, Throwable ex) {
        super("Get author with author id " + authorId + " exception", ex);
        this.authorId = authorId;
    }
}