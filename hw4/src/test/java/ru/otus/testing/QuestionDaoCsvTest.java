package ru.otus.testing;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.config.CsvResourceName;
import ru.otus.config.LocaleProvider;
import ru.otus.config.СorrectAnswersPass;
import ru.otus.dao.QuestionDaoCsv;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionDaoCsv")
@SpringBootTest
public class QuestionDaoCsvTest {
    private static final String CSV_RESOURCE_NAME = "questionsTest.csv";

    @Autowired
    private QuestionDaoCsv questionDao;

    @MockBean
    private CsvResourceName resourceName;

    @MockBean
    private LocaleProvider localeProvider;

    @MockBean
    private СorrectAnswersPass сorrectAnswersPass;


    @DisplayName("Test getQuestions")
    @Test
    public void shouldCorrectQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("question1", Arrays.asList(
                new Answer("answer1", true),
                new Answer("answer2", true),
                new Answer("answer3", true)
        )));
        questions.add(new Question("question2", Arrays.asList(
                new Answer("answer4", false),
                new Answer("answer5", true),
                new Answer("answer6", false)
        )));
        questions.add(new Question("question3", Arrays.asList(
                new Answer("answer7", false),
                new Answer("answer8", false),
                new Answer("answer9", false)
        )));
        given(resourceName.getCsvResourceName()).willReturn(CSV_RESOURCE_NAME);
       assertThat(questionDao.getQuestions()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(questions);
    }
}
