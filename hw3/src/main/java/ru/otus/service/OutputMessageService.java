package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OutputMessageService implements OutputMessage {

    private final LocalizationMessageService localizationMessageService;

    private final IOService ioService;

    @Override
    public void printConsoleMessageGreetings() {
        ioService.out(localizationMessageService.getLocalizedMessage("message.greetings"));
    }

    @Override
    public void printConsoleCorrectOrNotCorrectAnswer(boolean isCorrect) {
        ioService.out(localizationMessageService.getLocalizedMessage(isCorrect ? "message.answer.correct" : "message.answer.not_correct"));
    }
}
