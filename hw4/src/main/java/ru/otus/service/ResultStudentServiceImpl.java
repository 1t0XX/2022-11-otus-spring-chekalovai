package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Test;

@Service
@RequiredArgsConstructor
public class ResultStudentServiceImpl implements ResultStudentService {

    private final MessageService messageService;

    @Override
    public String outResultInfoTest(Test test, int correctAnswers) {
        String student = test.getUserNameAndSurname();
        var rightAnswers = test.getCorrectAnswers();
        var total = test.getTotalAnswers();
        return String.format(messageService.getMessage("test.result.pattern"), student, total,
                rightAnswers, correctAnswers, determineTestResult(rightAnswers, correctAnswers));
    }

    private String determineTestResult(int rightAnswers, int correctAnswers) {
        return messageService.getMessage(rightAnswers >= correctAnswers ? "message.test.passed" : "message.test.failed");
    }
}