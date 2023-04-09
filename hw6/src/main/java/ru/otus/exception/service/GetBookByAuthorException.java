package ru.otus.exception.service;

import lombok.Getter;

@Getter
public class GetBookByAuthorException extends RuntimeException {

    private final Long id;

    public GetBookByAuthorException(Long id, Throwable ex) {
        super("Get books with author id " + id + " exception", ex);
        this.id = id;
    }

}
