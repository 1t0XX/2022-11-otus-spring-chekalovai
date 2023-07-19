package ru.otus.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Long id;

    private String name;

    private Author author;

    private Genre genre;

}