package ru.otus.service;

import ru.otus.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre getGenreById(String id);

    Genre saveGenre(Genre genre);

    void deleteGenre(String id);
}