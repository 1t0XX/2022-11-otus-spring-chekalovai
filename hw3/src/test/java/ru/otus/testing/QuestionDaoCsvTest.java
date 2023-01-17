package ru.otus.testing;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.config.CsvResourceName;
import ru.otus.dao.QuestionDaoCsv;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionDaoCsv")
@ExtendWith(MockitoExtension.class)
public class QuestionDaoCsvTest {
    private static final String CSV_RESOURCE_NAME = "questionsTest.csv";

    @Mock
    private CsvResourceName resourceName;

    @InjectMocks
    QuestionDaoCsv questionDaoCsv;


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
       assertThat(questionDaoCsv.getQuestions()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(questions);
    }
}
