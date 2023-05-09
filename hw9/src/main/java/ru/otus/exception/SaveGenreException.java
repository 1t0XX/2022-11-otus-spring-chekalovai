package ru.otus.exception;

import lombok.Getter;
import ru.otus.model.Genre;

@Getter
public class SaveGenreException extends RuntimeException {

    private final Genre genre;

    public SaveGenreException(Genre genre, Throwable ex) {
        super(ex);
        this.genre = genre;
    }
}