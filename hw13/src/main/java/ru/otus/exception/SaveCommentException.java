package ru.otus.exception;

import lombok.Getter;
import ru.otus.model.CommentBook;

@Getter
public class SaveCommentException extends ResourceNotFoundException {

    private final CommentBook commentBook;

    public SaveCommentException(CommentBook commentBook, Throwable ex) {
        super(commentBook.toString(), ex);
        this.commentBook = commentBook;
    }
}