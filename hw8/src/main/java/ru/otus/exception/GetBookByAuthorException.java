package ru.otus.exception;

import lombok.Getter;
import ru.otus.model.Author;

@Getter
public class GetBookByAuthorException extends RuntimeException {

    private final String id;

    public GetBookByAuthorException(String id, Throwable ex) {
        super("Get books with author id " + id + " exception", ex);
        this.id = id;
    }
}