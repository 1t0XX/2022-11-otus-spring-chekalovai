package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.otus.dto.Book;
import ru.otus.service.BookService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping(path = "/feign/api/book")
    public List<Book> bookList() {
        return bookService.getAll();
    }

    @GetMapping(path = {"/feign/api/book/{id}"})
    public Book getBook(@PathVariable("id") long bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping(path = "/feign/api/book")
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping(path = "/feign/api/book/{id}")
    public void deleteBook(@PathVariable("id") long bookId) {
        bookService.deleteBook(bookId);
    }

}
