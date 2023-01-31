package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Author {
    private final Long id;
    private final String name;
    private final String surName;

    public Author() {
        id = null;
        name = null;
        surName = null;
    }
}
