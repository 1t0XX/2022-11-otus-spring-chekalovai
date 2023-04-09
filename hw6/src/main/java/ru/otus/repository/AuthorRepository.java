package ru.otus.repository;

import ru.otus.entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> getAll();

    Optional<Author> getById(Long id);

    Author save(Author author);

    void delete(Long id);
}
