package ru.otus.exception.service;

import lombok.Getter;
import ru.otus.entity.Book;

@Getter
public class SaveBookException extends RuntimeException {

    private final Book book;

    public SaveBookException(Book book, Throwable ex) {
        super(ex);
        this.book = book;
    }

}
