package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.domain.Book;

import java.util.List;

public interface BookDAO {
    List<Book> getAll();
    Book getById(Long id);
    Book save(Book book);
    void delete(Long id);
    List<Book> getByAuthorId(Long id);
}
