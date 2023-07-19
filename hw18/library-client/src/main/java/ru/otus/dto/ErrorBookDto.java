package ru.otus.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorBookDto extends Book {
    private String message;

}
