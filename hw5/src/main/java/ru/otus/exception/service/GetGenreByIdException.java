package ru.otus.exception.service;

import lombok.Getter;

@Getter
public class GetGenreByIdException extends RuntimeException{

    private final Long genreId;

    public GetGenreByIdException(Long genreId, Throwable ex) {
        super("Get genre with id " + genreId + " exception", ex);
        this.genreId = genreId;
    }

}
