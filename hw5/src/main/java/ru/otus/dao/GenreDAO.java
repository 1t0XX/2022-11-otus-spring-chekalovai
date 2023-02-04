package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenreDAO {
    List<Genre> getAll();
    Genre getById(Long id);
    Genre save(Genre genre);
    void delete(Long id);
}
