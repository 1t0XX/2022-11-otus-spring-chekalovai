package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.LocaleProvider;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final LocaleProvider localeProvider;

    private final MessageSource messageSource;

    @Override
    public String getMessage(String code) {
        return messageSource.getMessage(code, new String[0], localeProvider.getLocale());
    }

}