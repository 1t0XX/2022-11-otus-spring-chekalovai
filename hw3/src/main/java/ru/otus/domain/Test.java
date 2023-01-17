package ru.otus.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class Test {

    private final User user;

    private int totalAnswers;

    private int correctAnswers;

    public String getUserNameAndSurname() {
        return user.getName() + " " + user.getSurName();
    }

    public void incAnswers(boolean isCorrectAnswer) {
        if (isCorrectAnswer) {
            correctAnswers++;
        }
        totalAnswers++;
    }
}