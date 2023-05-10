package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetBookByIdException extends RuntimeException {

    private final Long bookId;

    public GetBookByIdException(Long bookId, String ex) {
        super("Get book with id " + bookId + " exception" + ex);
        this.bookId = bookId;
    }
}