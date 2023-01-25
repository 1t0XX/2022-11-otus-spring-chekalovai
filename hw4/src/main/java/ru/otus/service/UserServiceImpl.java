package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IOService ioService;

    private final MessageService messageService;

    @Override
    public User getUser() {
        String name = ioService.readLine(messageService.getMessage("message.name"));
        String surName = ioService.readLine(messageService.getMessage("message.surname"));
        return new User(name, surName);
    }
}