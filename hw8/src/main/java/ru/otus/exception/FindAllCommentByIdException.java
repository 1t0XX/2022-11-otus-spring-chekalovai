package ru.otus.exception;

public class FindAllCommentByIdException extends RuntimeException {

    public FindAllCommentByIdException(Throwable ex) {
        super(ex);
    }
}