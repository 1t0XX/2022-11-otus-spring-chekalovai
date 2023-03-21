package ru.otus.repository;

import ru.otus.entity.CommentBook;

import java.util.List;
import java.util.Optional;

public interface CommentBookRepository {
    List<CommentBook> findAllByBookId(Long id);

    Optional<CommentBook> getById(Long id);

    CommentBook save(CommentBook commentBook);

    void delete(Long id);

}
