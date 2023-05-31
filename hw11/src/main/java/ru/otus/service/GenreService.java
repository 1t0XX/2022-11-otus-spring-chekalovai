package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Genre;


public interface GenreService {

    Flux<Genre> getAll();

    Mono<Genre> getGenreById(String id);

    Mono<Genre> saveGenre(Genre genre);

    Mono<Void> deleteGenre(String id);
}