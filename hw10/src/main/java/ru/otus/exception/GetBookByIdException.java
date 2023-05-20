package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetBookByIdException extends ResourceNotFoundException {

    private final Long bookId;

    public GetBookByIdException(Long bookId, Throwable ex) {
        super("Get book with id " + bookId + " exception", ex);
        this.bookId = bookId;
    }
}