package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.config.СorrectAnswersPass;
import ru.otus.domain.Answer;
import ru.otus.domain.Test;
import ru.otus.domain.User;

@Service
@RequiredArgsConstructor
public class ExecutorService implements Executor {
    private final QuestionsService questionsService;
    private final IOService ioService;
    private final PrepareService outputService;
    private final UserService userService;
    private final ResultStudentService resultStudentService;
    private final СorrectAnswersPass correctAnswersPass;
    private final MessageWriterService messageWriterService;

    @Override
    public void run() {
        messageWriterService.printLocalizedMessage("message.greetings");
        User user = userService.getUser();
        Test test = new Test(user);
        questionsService.getQuestions().forEach(question -> {
            String answerUser = outputService.prepareOutput(question);
            Answer answer = question.getAnswers().stream().filter(x -> x.getAnswer().equals(answerUser)).findFirst().orElse(null);
            boolean isCorrect = answer != null && answer.isCorrectAnswer();
            test.incAnswers(isCorrect);
            messageWriterService.printLocalizedMessage(isCorrect ? "message.answer.correct" : "message.answer.not_correct");
        });
        int correctAnswers = correctAnswersPass.getCorrectAnswersPass();
        ioService.out(resultStudentService.outResultInfoTest(test, correctAnswers));
    }
}