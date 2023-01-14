package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.config.AppProperties;

@Service
@RequiredArgsConstructor
public class LocalizationMessageServiceImpl implements LocalizationMessageService {

    private final AppProperties appProperties;

    private final MessageSource messageSource;

    @Override
    public String getLocalizedMessage(String code) {
        return messageSource.getMessage(code, new String[0], appProperties.getLocale());
    }
}
