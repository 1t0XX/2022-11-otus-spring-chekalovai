package ru.otus.exception;

import lombok.Getter;
import ru.otus.model.Author;

@Getter
public class SaveAuthorException extends ResourceNotFoundException {

    private final Author author;

    public SaveAuthorException(Author author, Throwable ex) {
        super(author.toString(), ex);
        this.author = author;
    }
}