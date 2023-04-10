package ru.otus.service;

import ru.otus.entity.CommentBook;

import java.util.List;

public interface CommentService {

    List<CommentBook> findAllByBookId(Long id);

    CommentBook getCommentById(Long id);

    CommentBook saveComment(CommentBook bookComment);

    void deleteComment(Long id);
}