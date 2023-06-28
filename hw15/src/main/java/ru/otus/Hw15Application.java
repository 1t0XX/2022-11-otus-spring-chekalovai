package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.services.AppRunner;

@SpringBootApplication
public class Hw15Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Hw15Application.class, args);
        context.getBean(AppRunner.class).run();
    }
}