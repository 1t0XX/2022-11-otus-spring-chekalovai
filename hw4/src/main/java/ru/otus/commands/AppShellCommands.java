package ru.otus.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.Availability;
import ru.otus.service.Executor;
import ru.otus.service.MessageService;
import ru.otus.service.MessageWriterService;

@ShellComponent
@RequiredArgsConstructor
public class AppShellCommands {

    private static final String AGREEMENT = "yes";

    private final Executor executor;

    private final MessageWriterService messageWriterService;

    private final MessageService messageService;

    private boolean isAgree = false;

    @ShellMethod(value = "Agree Test", key = {"go", "run"})
    public void agreeToTesting(@ShellOption(defaultValue = "yes") String userAnswer) {
        this.isAgree = userAnswer.equalsIgnoreCase(AGREEMENT);
        messageWriterService.printLocalizedMessage(isAgree ? "confirmation.of.agreement" : "confirmation.of.disagreement");
    }

    @ShellMethod(key = "start")
    @ShellMethodAvailability(value = "isAgreementToTestingReceived")
    public void executeTest() {
        executor.run();
        messageWriterService.printLocalizedMessage("testing.is.finished");
    }

    private Availability isAgreementToTestingReceived() {
        return isAgree ? Availability.available() : Availability.unavailable(messageService.getMessage("confirmation.consent"));
    }
}