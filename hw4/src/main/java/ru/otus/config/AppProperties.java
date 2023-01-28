package ru.otus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "app.properties")
@Getter
@Setter
public class AppProperties implements LocaleProvider, CsvResourceName, СorrectAnswersPass {

    private String csvResourceName;

    private int correctAnswersPass;

    private Locale locale;

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getCsvResourceName() {
        return csvResourceName;
    }

    @Override
    public int getCorrectAnswersPass() {
        return correctAnswersPass;
    }
}