package ru.otus.actuator;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EvenMinuteHealthIndicator implements HealthIndicator {
    @Override
    public Health health() {
        boolean everyThingIsDead = LocalDateTime.now().getMinute() % 2 == 0;
        return everyThingIsDead ? Health.down()
                .status(Status.DOWN)
                .withDetail("alarm", "Ничего не работает!")
                .build() : Health.up()
                .status(Status.UP)
                .withDetail("alarm", "Все работает!")
                .build();
    }
}
