package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {

    private final Long id;

    private final String name;

    private Author author;

    private Genre genre;

    public Book() {
        id = null;
        name = null;
    }

    public Book(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
