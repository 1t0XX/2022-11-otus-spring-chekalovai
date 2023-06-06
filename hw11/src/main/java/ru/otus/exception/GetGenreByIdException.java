package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetGenreByIdException extends ResourceNotFoundException {

    private final String genreId;

    public GetGenreByIdException(String genreId, Throwable ex) {
        super("Get genre with id " + genreId + " exception", ex);
        this.genreId = genreId;
    }
}