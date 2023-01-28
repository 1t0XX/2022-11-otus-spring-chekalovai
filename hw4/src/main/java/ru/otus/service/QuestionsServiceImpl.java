package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;

import java.util.List;
@Service
@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionDao questionDao;

    @Override
    public List<Question> getQuestions() {
        return questionDao.getQuestions();
    }
}