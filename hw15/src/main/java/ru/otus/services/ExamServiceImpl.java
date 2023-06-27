package ru.otus.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.Enrollee;

import static ru.otus.utils.ExamUtils.calculateExamResult;
import static ru.otus.utils.ExamUtils.delay;

@Service
@Slf4j
public class ExamServiceImpl implements ExamService {

    @Override
    public Enrollee examProcess(Enrollee enrollee) {
        log.info("Начало экзамена, {} прибыл на сдачу экзамена", enrollee.getName());
        delay();
        int points = calculateExamResult();
        enrollee.setExamScores(points);
        log.info("Абитуриент сдал вступительный экзамен на {} балов", enrollee.getExamScores());
        return enrollee;
    }
}