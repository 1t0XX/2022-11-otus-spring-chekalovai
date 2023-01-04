package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.domain.Answer;

@RequiredArgsConstructor
public class ExecutorService implements Executor {
    private final QuestionsService questionsService;
    private final IOService ioService;
    private final PrepareService outputService;

    @Override
    public void run() {
        questionsService.getQuestions().forEach(question -> {
            String answerUser = outputService.prepareOutput(question);
            Answer answer = question.getAnswers().stream().filter(x -> x.getAnswer().contains(answerUser)).findFirst().orElse(null);
            ioService.out(answer != null && answer.isCorrectAnswer() ? "Correct answer" : "Not correct answer");
        });
    }
}
