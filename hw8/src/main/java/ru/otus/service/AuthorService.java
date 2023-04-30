package ru.otus.service;

import ru.otus.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author getAuthorById(String id);

    Author saveAuthor(Author author);

    void deleteAuthor(String id);
}