package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Author;
import ru.otus.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(path = "/api/author")
    public Flux<Author> authorList() {
        return authorService.getAll();
    }

    @GetMapping(path = {"/api/author/{id}"})
    public Mono<Author> getAuthor(@PathVariable("id") String authorId) {
        return authorService.getAuthorById(authorId);
    }

    @PostMapping(path = "/api/author")
    public Mono<Void> saveAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @DeleteMapping(path = "/api/author/{id}")
    public Mono<Void> deleteAuthor(@PathVariable("id") String authorId) {
        return authorService.deleteAuthor(authorId);
    }
}
