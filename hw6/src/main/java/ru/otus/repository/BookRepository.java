package ru.otus.repository;

import ru.otus.entity.Author;
import ru.otus.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    List<Book> getAll();

    Optional<Book> getById(Long id);

    Book save(Book book);

    void delete(Long id);

    List<Book> getByAuthorId(Long id);
}
