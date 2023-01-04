package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class QuestionsServiceImpl implements QuestionsService {

    private final QuestionDao questionDao;

    @Override
    public List<Question> getQuestions() {
        return questionDao.getQuestions();
    }

}
