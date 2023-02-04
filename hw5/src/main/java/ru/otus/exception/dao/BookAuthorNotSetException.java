package ru.otus.exception.dao;

import lombok.Getter;

@Getter
public class BookAuthorNotSetException extends RuntimeException{

    public BookAuthorNotSetException() {
        super("Book author not set");
    }
    public BookAuthorNotSetException(Throwable ex) {
        super("Book author not set", ex);
    }

}
