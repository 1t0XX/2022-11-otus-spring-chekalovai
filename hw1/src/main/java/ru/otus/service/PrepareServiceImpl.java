package ru.otus.service;

import lombok.RequiredArgsConstructor;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;
@RequiredArgsConstructor
public class PrepareServiceImpl implements PrepareService {
    private final IOService ioService;

    @Override
    public String prepareOutput(Question question) {
        StringBuilder builder = new StringBuilder(question.getDescription() + "\n");
        int i = 0;
        for (Answer answer : question.getAnswers()) {
            builder.append(++i).append(")   ").append(answer.getAnswer()).append("\n");
        }
        ioService.out(builder.toString());
        return ioService.readString();
    }
}
