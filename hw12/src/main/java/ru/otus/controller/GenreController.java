package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Genre;
import ru.otus.service.GenreService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(path = "/api/genre")
    public List<Genre> genreList() {
        return genreService.getAll();
    }

    @GetMapping(path = {"/api/genre/{id}"})
    public Genre getGenre(@PathVariable("id") long genreId) {
        return genreService.getGenreById(genreId);

    }

    @PostMapping(path = "/api/genre")
    public Genre saveGenre(@RequestBody Genre genre) {
        return genreService.saveGenre(genre);
    }

    @DeleteMapping(path = "/api/genre/{id}")
    public void deleteGenre(@PathVariable("id") long genreId) {
        genreService.deleteGenre(genreId);
    }

}
