package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final IOService ioService;
    private static final String MSG_FOR_NAME = "Write you name: ";
    private static final String MSG_FOR_SURNAME= "Write you surname: ";

    @Override
    public User getUser() {

        String name = ioService.readLine(MSG_FOR_NAME);
        String surName = ioService.readLine(MSG_FOR_SURNAME);
        return new User(name, surName);
    }
}
