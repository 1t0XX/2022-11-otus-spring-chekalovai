package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.domain.Answer;
import ru.otus.domain.Test;
import ru.otus.domain.User;

@Service
public class ExecutorService implements Executor {
    private final QuestionsService questionsService;
    private final IOService ioService;
    private final PrepareService outputService;
    private final UserService userService;
    private final ResultStudentService resultStudentService;
    private final int correctAnswers;

    private static final String MSG_FOR_GREETINGS = "Welcome to the test students!";
    private static final String CORRECT_ANSWER = "Correct answer";
    private static final String NOT_CORRECT_ANSWER = "Not correct answer";

    public ExecutorService(QuestionsService questionsService, IOService ioService, PrepareService outputService,
                           UserService userService, ResultStudentService resultStudentService, @Value("${correct.answers.pass}") int correctAnswers) {
        this.questionsService = questionsService;
        this.ioService = ioService;
        this.outputService = outputService;
        this.userService = userService;
        this.resultStudentService = resultStudentService;
        this.correctAnswers = correctAnswers;
    }

    @Override
    public void run() {
        ioService.out(MSG_FOR_GREETINGS);
        User user = userService.getUser();
        Test test = new Test(user);
        questionsService.getQuestions().forEach(question -> {
            String answerUser = outputService.prepareOutput(question);
            Answer answer = question.getAnswers().stream().filter(x -> x.getAnswer().contains(answerUser)).findFirst().orElse(null);
            boolean isCorrect = answer != null && answer.isCorrectAnswer();
            test.incAnswers(isCorrect);
            ioService.out(isCorrect ? CORRECT_ANSWER : NOT_CORRECT_ANSWER);
        });
        ioService.out(resultStudentService.outResultInfoTest(test, correctAnswers));
    }
}
