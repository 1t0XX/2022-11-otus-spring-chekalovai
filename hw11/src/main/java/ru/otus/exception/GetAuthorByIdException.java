package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetAuthorByIdException extends ResourceNotFoundException {

    private final String authorId;

    public GetAuthorByIdException(String authorId, Throwable ex) {
        super("Get author with author id " + authorId + " exception", ex);
        this.authorId = authorId;
    }
}