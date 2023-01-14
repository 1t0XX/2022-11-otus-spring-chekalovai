package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.User;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final IOService ioService;

    private final LocalizationMessageService localizationMessageService;

    @Override
    public User getUser() {
        String name = ioService.readLine(localizationMessageService.getLocalizedMessage("message.name"));
        String surName = ioService.readLine(localizationMessageService.getLocalizedMessage("message.surname"));
        return new User(name, surName);
    }
}
