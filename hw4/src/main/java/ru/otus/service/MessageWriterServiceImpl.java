package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageWriterServiceImpl implements MessageWriterService {

    private final IOService ioService;

    private final MessageService messageService;

    @Override
    public void printLocalizedMessage(String code) {
        ioService.out(messageService.getMessage(code));
    }
}