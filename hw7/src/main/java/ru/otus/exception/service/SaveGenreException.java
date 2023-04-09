package ru.otus.exception.service;

import lombok.Getter;
import ru.otus.entity.Genre;

@Getter
public class SaveGenreException extends RuntimeException {

    private final Genre genre;

    public SaveGenreException(Genre genre, Throwable ex) {
        super(ex);
        this.genre = genre;
    }
}