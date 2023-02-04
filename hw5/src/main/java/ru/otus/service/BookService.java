package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();
    Book getBookById(Long id);
    Book saveBook(Book book);
    void deleteBook(Long id);
    List<Book> getBookByAuthor(Long id);
}
