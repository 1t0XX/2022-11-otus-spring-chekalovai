package ru.otus.service;

import org.springframework.stereotype.Service;
import ru.otus.domain.Test;

@Service
public class ResultStudentServiceImpl implements ResultStudentService {

    private static final String TEST_RESULT_PATTERN = "Dear %s. Total questions amount: %d. " +
            "Right answers: %d, must be at least %d. %s";
    private static final String MSG_TEST_PASSED = "Test passed!";
    private static final String MSG_TEST_FAILED = "Test failed. Try again.";

    @Override
    public String outResultInfoTest(Test test, int correctAnswers) {
        String student = test.getUserNameAndSurname();
        var rightAnswers = test.getCorrectAnswers();
        var total = test.getTotalAnswers();
        return String.format(TEST_RESULT_PATTERN, student, total,
                rightAnswers, correctAnswers, determineTestResult(rightAnswers, correctAnswers));
    }

    private String determineTestResult(int rightAnswers, int correctAnswers) {
        return rightAnswers >= correctAnswers ? MSG_TEST_PASSED : MSG_TEST_FAILED;
    }
}
