package ru.otus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties implements LocaleProvider, LocalizedCsvResourceName, СorrectAnswersPass {
    private String csvResourceName;
    private int correctAnswersPass;
    private Locale locale;

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String getLocalizedCsvResourceName() {
        return locale.toString() + "_" + csvResourceName;
    }

    @Override
    public int getCorrectAnswersPass() {
        return correctAnswersPass;
    }
}