package ru.otus.exception.service;

public class FindAllCommentByIdException extends RuntimeException {

    public FindAllCommentByIdException(Throwable ex) {
        super(ex);
    }
}