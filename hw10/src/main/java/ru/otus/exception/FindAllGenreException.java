package ru.otus.exception;

public class FindAllGenreException extends ResourceNotFoundException {

    public FindAllGenreException(Throwable ex) {
        super("FindAllGenreException", ex);
    }
}