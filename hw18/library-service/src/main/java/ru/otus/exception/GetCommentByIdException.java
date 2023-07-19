package ru.otus.exception;

import lombok.Getter;

@Getter
public class GetCommentByIdException extends ResourceNotFoundException {

    private final Long commentId;

    public GetCommentByIdException(Long commentId, Throwable ex) {
        super("Get comment with id " + commentId + " exception", ex);
        this.commentId = commentId;
    }
}