package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetAuthorByIdException extends RuntimeException {

    private final String authorId;

    public GetAuthorByIdException(String authorId, String ex) {
        super("Get author with id " + authorId + " exception" + ex);
        this.authorId = authorId;
    }
}