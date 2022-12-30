package ru.otus.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.dao.QuestionDaoCsv;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Class QuestionDaoCsv")
public class QuestionDaoCsvTest {
    @DisplayName("Test getQuestions")
    @Test
    public void shouldCorrectQuestions() {
        QuestionDao questionDao = new QuestionDaoCsv("questionsTest.csv");
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
       assertThat(questions).hasSameElementsAs(questionDao.getQuestions());
    }
}
