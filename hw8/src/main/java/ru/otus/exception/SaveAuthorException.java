package ru.otus.exception;

import lombok.Getter;
import ru.otus.model.Author;

@Getter
public class SaveAuthorException extends RuntimeException {

    private final Author author;

    public SaveAuthorException(Author author, Throwable ex) {
        super(ex);
        this.author = author;
    }
}