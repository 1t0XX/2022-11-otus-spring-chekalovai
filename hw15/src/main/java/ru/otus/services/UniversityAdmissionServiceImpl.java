package ru.otus.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.Enrollee;
import ru.otus.model.Student;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UniversityAdmissionServiceImpl implements UniversityAdmissionService {

    private static final int MIN_REQUIRED_POINTS_NUMBER = 42;

    @Override
    public List<Student> accept(List<Enrollee> enrollees) {
        log.info("{} человек сдали вступительные экзамены и получили определенное количество проходных баллов", enrollees.size());
        List<Student> students = new ArrayList<>();
        enrollees.forEach(enrollee -> {
            if (enrollee.getExamScores() >= MIN_REQUIRED_POINTS_NUMBER) {
                var student = new Student();
                student.setName(enrollee.getName());
                log.info("Абитуриент {} зачислен в университет", student.getName());
                students.add(student);
            } else {
                log.info("Абитуриент {} не зачислен в университет", enrollee.getName());
            }
        });
        log.info("{} человек набрали достаточное количество баллов и были зачислены в университет", students.size());
        return students;
    }
}