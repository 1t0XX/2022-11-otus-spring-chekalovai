package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Author;


public interface AuthorService {

    Flux<Author> getAll();

    Mono<Author> getAuthorById(String id);

    Mono<Void> saveAuthor(Author author);

    Mono<Void> deleteAuthor(String id);
}