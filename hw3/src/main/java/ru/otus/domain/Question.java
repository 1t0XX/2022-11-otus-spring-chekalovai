package ru.otus.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Question {

    private String description;

    private List<Answer> answers;
}
