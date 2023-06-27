package ru.otus.services;


import ru.otus.model.Enrollee;
import ru.otus.model.Student;

import java.util.List;

public interface UniversityAdmissionService {

    List<Student> accept(List<Enrollee> enrollees);

}
