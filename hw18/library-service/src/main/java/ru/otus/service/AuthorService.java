package ru.otus.service;

import ru.otus.model.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author getAuthorById(Long id);

    Author saveAuthor(Author author);

    void deleteAuthor(Long id);
}