package ru.otus.services;


import ru.otus.model.Employee;
import ru.otus.model.Student;

import java.util.List;

public interface GraduateWorkDefenseService {

    List<Employee> defenseGraduateWork(List<Student> students);

}