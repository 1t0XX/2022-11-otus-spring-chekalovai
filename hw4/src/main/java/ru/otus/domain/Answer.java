package ru.otus.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Answer {

    private String answer;

    private boolean correctAnswer;
}