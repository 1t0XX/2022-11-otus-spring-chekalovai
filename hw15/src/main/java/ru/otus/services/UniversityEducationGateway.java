package ru.otus.services;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.model.Employee;
import ru.otus.model.Enrollee;

import java.util.List;

@MessagingGateway
public interface UniversityEducationGateway {

    @Gateway(requestChannel = "enrolleeChannel", replyChannel = "employeeChannel")
    List<Employee> startLearning(List<Enrollee> enrollees);

}