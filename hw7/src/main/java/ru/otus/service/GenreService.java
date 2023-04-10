package ru.otus.service;

import ru.otus.entity.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre getGenreById(Long id);

    Genre saveGenre(Genre genre);

    void deleteGenre(Long id);
}