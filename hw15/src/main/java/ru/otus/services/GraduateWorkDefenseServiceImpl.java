package ru.otus.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.model.Employee;
import ru.otus.model.Student;


import java.util.ArrayList;

import java.util.List;

@Service
@Slf4j
public class GraduateWorkDefenseServiceImpl implements GraduateWorkDefenseService {
    @Override
    public List<Employee> defenseGraduateWork(List<Student> students) {
        log.info("{} человек вышли на защиту дипломной работы", students.size());
        List<Employee> employees = new ArrayList<>();
        students.forEach(student -> {
            if (student.isPassedDiploma()) {
                log.info("Студент {} успешно сдал дипломную работу", student.getName());
                employees.add(new Employee(student.getName()));
            } else {
                log.info("Студент {} не сдал дипломную работу", student.getName());
            }
        });
        return employees;
    }
}