package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetGenreByIdException extends RuntimeException {

    private final String genreId;

    public GetGenreByIdException(String genreId, String ex) {
        super("Get genre with id " + genreId + " exception" + ex);
        this.genreId = genreId;
    }
}