package ru.otus.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.Student;

import static ru.otus.utils.ExamUtils.delay;
import static ru.otus.utils.ExamUtils.isPassedDiploma;


@Service
@Slf4j
public class GraduateWorkPrepareServiceImpl implements GraduateWorkPrepareService {
    @Override
    public Student prepareGraduateWork(Student student) {
        log.info("Начало выполнения дипломной работы студентом {}", student.getName());
        delay();
        var passedDiploma = isPassedDiploma();
        student.setPassedDiploma(passedDiploma);
        log.info("Окончание выполнения дипломной работы студентом {}", student.getName());
        return student;
    }
}