package ru.otus.exception.service;

import lombok.Getter;

@Getter
public class GetAuthorByIdException extends RuntimeException{

    private final Long authorId;

    public GetAuthorByIdException(Long authorId, Throwable ex) {
        super("Get author with id " + authorId + " exception", ex);
        this.authorId = authorId;
    }

}
