package ru.otus.testing;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
import ru.otus.service.QuestionsServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("Class QuestionsServiceImplTest")
@SpringBootTest
public class QuestionsServiceImplTest {
    @MockBean
    private QuestionDao questionDao;
    @Autowired
    private QuestionsServiceImpl questionsService;

    @DisplayName("Test QuestionsServiceImpl")
    @Test
    void shouldReturnCorrectQuestionsList() {
        List<Question> testQuestions = new ArrayList<>();
        testQuestions.add(new Question("question1", Arrays.asList(
                new Answer("answer1", true),
                new Answer("answer2", true),
                new Answer("answer3", true)
        )));
        testQuestions.add(new Question("question2", Arrays.asList(
                new Answer("answer4", false),
                new Answer("answer5", true),
                new Answer("answer6", false)
        )));
        testQuestions.add(new Question("question3", Arrays.asList(
                new Answer("answer7", false),
                new Answer("answer8", false),
                new Answer("answer9", false)
        )));

        given(questionDao.getQuestions()).willReturn(testQuestions);

        assertThat(questionsService.getQuestions())
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(testQuestions);
    }
}