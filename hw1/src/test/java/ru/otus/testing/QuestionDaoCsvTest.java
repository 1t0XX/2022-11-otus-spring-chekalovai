package ru.otus.testing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;
import ru.otus.utils.QuestionDaoCsv;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Class QuestionDaoCsv")
public class QuestionDaoCsvTest {

    @DisplayName("Test getQuestions")
    @Test
    public void shouldCorrectQuestions() {
        QuestionDao questionDao = new QuestionDaoCsv("questions.csv");
        List<Question> questions = questionDao.getQuestions();
        assertEquals(5, questions.size());
        assertEquals("Select reference data type in Java?", questions.get(3).getDescription());
        assertEquals("4", questions.get(4).getAnswer().getCorrectAnswer());
    }

}