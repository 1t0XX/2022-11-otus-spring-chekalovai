package ru.otus.service;

import ru.otus.model.Author;
import ru.otus.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAll();

    Book getBookById(String id);

    Book saveBook(Book book);

    void deleteBookById(String id);

    List<Book> getBookByAuthorId(String id);
}