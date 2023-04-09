package ru.otus.exception.service;

import lombok.Getter;

@Getter
public class GetCommentByIdException extends RuntimeException {

    private final Long commentId;

    public GetCommentByIdException(Long commentId, String ex) {
        super("Get comment with id " + commentId + " exception" + ex);
        this.commentId = commentId;
    }
}