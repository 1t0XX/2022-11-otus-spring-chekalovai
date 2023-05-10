package ru.otus.exception;

import lombok.Getter;
import ru.otus.model.CommentBook;

@Getter
public class SaveCommentException extends RuntimeException {

    private final CommentBook commentBook;

    public SaveCommentException(CommentBook commentBook, Throwable ex) {
        super(ex);
        this.commentBook = commentBook;
    }
}