package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.model.Book;
import ru.otus.service.BookService;



@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(path = "/api/book")
    public Flux<Book> bookList() {
        return bookService.getAll();
    }

    @GetMapping(path = {"/api/book/{id}"})
    public Mono<Book> getBook(@PathVariable("id") String bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping(path = "/api/book")
    public Mono<Book> saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping(path = "/api/book/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") String bookId) {
        return bookService.deleteBook(bookId);
    }

}
