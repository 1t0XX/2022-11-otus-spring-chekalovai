package ru.otus.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.Employee;
import ru.otus.model.Enrollee;


import java.util.List;

import static ru.otus.utils.ExamUtils.delay;
import static ru.otus.utils.ExamUtils.getComeToTheExam;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppRunnerImpl implements AppRunner {

    private final UniversityEducationGateway universityEducationGateway;

    @Override
    public void run() {
            List<Enrollee> enrollees = getComeToTheExam();
            log.info("{} человек прибыло на вступительный экзамен", enrollees.size());
            List<Employee> employees = universityEducationGateway.startLearning(enrollees);
            log.info("{} человек закончили обучение и получили дипломы", employees.size());
            delay();
    }
}