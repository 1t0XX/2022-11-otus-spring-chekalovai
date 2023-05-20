package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.model.Author;
import ru.otus.service.AuthorService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(path = "/api/author")
    public List<Author> authorList() {
        return authorService.getAll();
    }

    @GetMapping(path = {"/api/author/{id}"})
    public Author getAuthor(@PathVariable("id") long authorId) {
        return authorService.getAuthorById(authorId);
    }

    @PostMapping(path = "/api/author")
    public Author saveAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @DeleteMapping(path = "/api/author/{id}")
    public void deleteAuthor(@PathVariable("id") long authorId) {
        authorService.deleteAuthor(authorId);
    }
}
