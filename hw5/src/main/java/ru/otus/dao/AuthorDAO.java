package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDAO {
    List<Author> getAll();
    Author getById(Long id);
    Author save(Author author);
    void delete(Long id);
}
