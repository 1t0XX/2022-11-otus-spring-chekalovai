package ru.otus.exception;

public class FindAllCommentByIdException extends ResourceNotFoundException {

    public FindAllCommentByIdException(Throwable ex) {
        super("FindAllCommentByIdException", ex);
    }
}