package ru.otus.exception.service;

import lombok.Getter;
import ru.otus.domain.Author;

@Getter
public class SaveAuthorException extends RuntimeException{

    private final Author author;

    public SaveAuthorException(Author author, Throwable ex) {
        super(ex);
        this.author = author;
    }
}
