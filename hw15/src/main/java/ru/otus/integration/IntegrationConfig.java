package ru.otus.integration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.services.ExamService;
import ru.otus.services.GraduateWorkDefenseService;
import ru.otus.services.GraduateWorkPrepareService;
import ru.otus.services.UniversityAdmissionService;

@Configuration
@AllArgsConstructor
public class IntegrationConfig {

    private final ExamService examService;

    private final GraduateWorkDefenseService graduateWorkDefenseService;

    private final GraduateWorkPrepareService graduateWorkPrepareService;

    private final UniversityAdmissionService universityAdmissionService;


    @Bean
    public QueueChannel enrolleeChannel() {
        return MessageChannels.queue(10).get();
    }

    @Bean
    public PublishSubscribeChannel employeeChannel() {
        return MessageChannels.publishSubscribe().get();
    }


    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(1000).get();
    }

    @Bean
    public IntegrationFlow enrolleeFlow(){
        return IntegrationFlows
                .from(enrolleeChannel())
                .split()
                .transform(examService::examProcess)
                .aggregate()
                .handle(universityAdmissionService, "accept")
                .channel("enrolleeFlow.output")
                .get();
    }

    @Bean
    public IntegrationFlow studentFlow() {
        return IntegrationFlows.from("enrolleeFlow.output")
                .split()
                .transform(graduateWorkPrepareService::prepareGraduateWork)
                .aggregate()
                .handle(graduateWorkDefenseService, "defenseGraduateWork")
                .channel(employeeChannel())
                .get();
    }

}