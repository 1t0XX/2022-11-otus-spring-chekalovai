package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Genre;
import ru.otus.service.GenreService;


@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(path = "/api/genre")
    public Flux<Genre> genreList() {
        return genreService.getAll();
    }

    @GetMapping(path = {"/api/genre/{id}"})
    public Mono<Genre> getGenre(@PathVariable("id") String genreId) {
        return genreService.getGenreById(genreId);
    }

    @PostMapping(path = "/api/genre")
    public Mono<Void> saveGenre(@RequestBody Genre genre) {
        return genreService.saveGenre(genre);
    }

    @DeleteMapping(path = "/api/genre/{id}")
    public Mono<Void> deleteGenre(@PathVariable("id") String genreId) {
        return genreService.deleteGenre(genreId);
    }

}
