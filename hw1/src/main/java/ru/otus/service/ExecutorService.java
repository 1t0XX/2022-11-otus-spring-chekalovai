package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class ExecutorService implements Executor {
    private final QuestionsService questionsService;
    private final IOStreamsService ioService;

    @Override
    public void run() {
        List<Question> questions = questionsService.getQuestions();
        for (Question question : questions) {
            ioService.out(question.getDescription() + "\n" + question.getAnswer().getAnswers().toString());
            String answerUser = ioService.readString();
            boolean correctAnswer = question.getAnswer().getCorrectAnswer().contains(answerUser);
            ioService.out(correctAnswer ? "Correct answer" : "Not correct answer");
        }
    }
}
