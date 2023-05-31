package ru.otus.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Book;

public interface BookService {
    Flux<Book> getAll();

    Mono<Book> getBookById(String id);

    Mono<Book> saveBook(Book book);

    Mono<Void> deleteBook(String id);

}