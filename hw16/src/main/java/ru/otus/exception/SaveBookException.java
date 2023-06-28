package ru.otus.exception;

import lombok.Getter;
import ru.otus.model.Book;

@Getter
public class SaveBookException extends ResourceNotFoundException {

    private final Book book;

    public SaveBookException(Book book, Throwable ex) {
        super(book.toString(), ex);
        this.book = book;
    }
}