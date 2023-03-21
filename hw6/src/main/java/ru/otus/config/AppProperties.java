package ru.otus.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "app.properties")
@Getter
@Setter
public class AppProperties implements LocaleProvider {
    private Locale locale;

    @Override
    public Locale getLocale() {
        return locale;
    }

}