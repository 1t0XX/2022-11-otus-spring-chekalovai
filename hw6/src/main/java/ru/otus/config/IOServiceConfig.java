package ru.otus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.IOService;
import ru.otus.service.IOStreamsService;

@Configuration
public class IOServiceConfig {
    @Bean
    public IOService ioService() {
        return new IOStreamsService(System.out, System.in);
    }
}
