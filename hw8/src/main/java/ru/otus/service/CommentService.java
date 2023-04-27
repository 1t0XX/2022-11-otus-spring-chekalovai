package ru.otus.service;

import ru.otus.model.Book;
import ru.otus.model.CommentBook;

import java.util.List;

public interface CommentService {

    List<CommentBook> findAllByBookId(String id);

    CommentBook getCommentById(String id);

    CommentBook saveComment(CommentBook bookComment);

    void deleteComment(String id);
}