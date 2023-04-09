package ru.otus.repository;

import ru.otus.entity.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> getAll();

    Optional<Genre> getById(Long id);

    Genre save(Genre genre);

    void delete(Long id);
}
