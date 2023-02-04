package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Genre {
    private final Long id;
    private final String name;

    public Genre() {
        id = null;
        name = null;
    }
}
