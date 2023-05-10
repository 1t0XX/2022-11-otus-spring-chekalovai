package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetGenreByIdException extends RuntimeException {

    private final Long genreId;

    public GetGenreByIdException(Long genreId, String ex) {
        super("Get genre with id " + genreId + " exception" + ex);
        this.genreId = genreId;
    }
}