package ru.otus.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private Long id;
    private String name;
    private String surName;

}