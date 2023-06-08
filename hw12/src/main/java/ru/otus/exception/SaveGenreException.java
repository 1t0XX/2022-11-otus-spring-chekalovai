package ru.otus.exception;

import lombok.Getter;
import ru.otus.model.Genre;

@Getter
public class SaveGenreException extends ResourceNotFoundException {

    private final Genre genre;

    public SaveGenreException(Genre genre, Throwable ex) {
        super(genre.toString(), ex);
        this.genre = genre;
    }
}