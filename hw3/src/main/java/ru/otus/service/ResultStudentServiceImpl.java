package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Test;

@Service
@RequiredArgsConstructor
public class ResultStudentServiceImpl implements ResultStudentService {

    private final LocalizationMessageService localizationMessageService;

    @Override
    public String outResultInfoTest(Test test, int correctAnswers) {
        String student = test.getUserNameAndSurname();
        var rightAnswers = test.getCorrectAnswers();
        var total = test.getTotalAnswers();
        return String.format(localizationMessageService.getLocalizedMessage("test.result.pattern"), student, total,
                rightAnswers, correctAnswers, determineTestResult(rightAnswers, correctAnswers));
    }

    private String determineTestResult(int rightAnswers, int correctAnswers) {
        return localizationMessageService.getLocalizedMessage(rightAnswers >= correctAnswers ? "message.test.passed" : "message.test.failed");
    }
}
