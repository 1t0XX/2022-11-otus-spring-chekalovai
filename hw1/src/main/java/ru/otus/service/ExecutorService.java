package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.domain.Question;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class ExecutorService implements Executor {

    private final QuestionsService questionsService;

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        List<Question> questions = questionsService.getQuestions();
        questions.forEach(question -> {
            System.out.println(question.getDescription());
            System.out.println("Choose one answer: ");
            System.out.println(String.join(", ", question.getAnswer()));
            String answer = sc.nextLine();
            System.out.println(answer.contains(question.getCorrectAnswer()) ? "Correct answer" : "Not correct answer");
        });
    }
}
