package ru.otus.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.step.tasklet.MethodInvokingTaskletAdapter;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.util.Assert;
import ru.otus.model.*;
import ru.otus.service.ClearService;
import ru.otus.service.IdSynchronizerService;
import ru.otus.writer.MongoItemWriterWithExtractedId;


import javax.persistence.EntityManagerFactory;
import java.util.List;


@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    private final ClearService clearService;

    private final IdSynchronizerService idSync;
    private static final String MIGRATION_JOB_NAME = "LIBRARY-MIGRATION-JOB";

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final MongoTemplate mongoTemplate;

    @Bean
    public Flow splitFlow(Flow authorMigrationFlow, Flow genreMigrationFlow) {
        return new FlowBuilder<SimpleFlow>("authorGenreMigrateFlow")
                .split(taskExecutor())
                .add(authorMigrationFlow, genreMigrationFlow)
                .build();
    }

    @Bean
    public Flow mainFlow(Flow splitFlow, Step bookMigrationStep, Step prepareStep) {
        return new FlowBuilder<SimpleFlow>("authorGenreMigrateFlow")
                .start(prepareStep)
                .next(splitFlow)
                .next(bookMigrationStep)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor("springBatchTaskExecutor");
    }

    @Bean
    public Flow authorMigrationFlow(Step authorMigrationStep) {
        return new FlowBuilder<Flow>("migrateAuthorFlow")
                .start(authorMigrationStep)
                .build();
    }

    @Bean
    public Flow genreMigrationFlow(Step genreMigrationStep) {
        return new FlowBuilder<Flow>("migrateGenreFlow")
                .start(genreMigrationStep)
                .build();
    }

    @Bean
    public Step bookMigrationStep() {
        return stepBuilderFactory.get("bookMigrationStep")
                .<Book, MongoBook>chunk(5)
                .reader(jpaBookReader())
                .processor(bookProcessor())
                .writer(mongoBookWriter())
                .build();
    }

    @Bean
    public Step authorMigrationStep() {
        return stepBuilderFactory.get("authorMigrationStep")
                .<Author, MongoAuthor>chunk(5)
                .reader(jpaAuthorReader())
                .processor(authorProcessor())
                .writer(mongoAuthorWriter())
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void beforeWrite(List<? extends MongoAuthor> items) {
                        log.info("Начало записи");
                    }

                    @Override
                    public void afterWrite(List<? extends MongoAuthor> items) {
                        log.info("Конец записи");
                        items.forEach(item -> idSync.putIdLink(item.getOldId(), item.getId(), IdSynchronizerService.EntryType.AUTHOR));
                    }

                    @Override
                    public void onWriteError(Exception exception, List<? extends MongoAuthor> items) {
                        log.info("Ошибка записи");
                    }

                })
                .transactionAttribute(attributesForTransaction())
                .build();
    }

    @Bean
    public Step genreMigrationStep() {
        return stepBuilderFactory.get("genreMigrationStep")
                .<Genre, MongoGenre>chunk(5)
                .reader(jpaGenreReader())
                .processor(genreProcessor())
                .writer(mongoGenreWriter())
                .listener(new ItemWriteListener<>() {
                    @Override
                    public void beforeWrite(List<? extends MongoGenre> items) {
                        log.info("Начало записи");
                    }

                    @Override
                    public void afterWrite(List<? extends MongoGenre> items) {
                        log.info("Конец записи");
                        items.forEach(item -> idSync.putIdLink(item.getOldId(), item.getId(), IdSynchronizerService.EntryType.GENRE));
                    }

                    @Override
                    public void onWriteError(Exception exception, List<? extends MongoGenre> items) {
                        log.info("Ошибка записи");
                    }

                })
                .transactionAttribute(attributesForTransaction())
                .build();
    }

    @Bean
    public JpaCursorItemReader<Book> jpaBookReader() {
        return new JpaCursorItemReaderBuilder<Book>()
                .name("bookItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT b FROM Book b")
                .build();
    }

    @Bean
    public JpaCursorItemReader<Author> jpaAuthorReader() {
        return new JpaCursorItemReaderBuilder<Author>()
                .name("authorItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT a FROM Author a")
                .build();
    }

    @Bean
    public JpaCursorItemReader<Genre> jpaGenreReader() {
        return new JpaCursorItemReaderBuilder<Genre>()
                .name("genreItemReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("SELECT g FROM Genre g")
                .build();
    }

    @Bean
    public ItemProcessor<Book, MongoBook> bookProcessor() {
        return book -> MongoBook.toMongoEntity(book,
                idSync.getIdByOld(book.getAuthor().getId(), IdSynchronizerService.EntryType.AUTHOR),
                idSync.getIdByOld(book.getGenre().getId(), IdSynchronizerService.EntryType.GENRE));
    }

    @Bean
    public ItemProcessor<Author, MongoAuthor> authorProcessor() {
        return MongoAuthor::toMongoEntity;
    }


    @Bean
    public ItemProcessor<Genre, MongoGenre> genreProcessor() {
        return MongoGenre::toMongoEntity;
    }

    @Bean
    public ItemWriter<MongoBook> mongoBookWriter() {
        return new MongoItemWriterBuilder<MongoBook>()
                .template(mongoTemplate)
                .collection("book")
                .build();
    }

    @Bean
    public ItemWriter<MongoAuthor> mongoAuthorWriter() {
        return getMongoItemWriterWithExtractedId(mongoTemplate, "author");
    }

    @Bean
    public MongoItemWriter<MongoGenre> mongoGenreWriter() {
        return getMongoItemWriterWithExtractedId(mongoTemplate, "genre");
    }

    @Bean
    public Job migrationJob(Flow mainFlow) {
        return jobBuilderFactory.get(MIGRATION_JOB_NAME)
                .start(mainFlow)
                .end()
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(@NonNull JobExecution jobExecution) {
                        log.info("Начало job");
                    }

                    @Override
                    public void afterJob(@NonNull JobExecution jobExecution) {
                        log.info("Конец job");
                    }
                })
                .build();
    }

    private DefaultTransactionAttribute attributesForTransaction() {
        DefaultTransactionAttribute attribute = new DefaultTransactionAttribute();
        attribute.setPropagationBehavior(Propagation.NOT_SUPPORTED.value());
        attribute.setIsolationLevel(Isolation.DEFAULT.value());
        return attribute;
    }

    private <T extends EntityWithStringId> MongoItemWriter<T> getMongoItemWriterWithExtractedId(MongoOperations template,
                                                                                                String collection) {
        Assert.notNull(template, "template is required.");
        MongoItemWriterWithExtractedId<T> writer = new MongoItemWriterWithExtractedId<>(collection);
        writer.setTemplate(template);
        writer.setDelete(false);
        writer.setCollection(collection);
        return writer;
    }

    @Bean
    public Step prepareStep() {
        return this.stepBuilderFactory.get("prepareStep")
                .tasklet(cleanTasklet())
                .build();
    }

    @Bean
    public MethodInvokingTaskletAdapter cleanTasklet() {
        MethodInvokingTaskletAdapter adapter = new MethodInvokingTaskletAdapter();
        adapter.setTargetObject(clearService);
        adapter.setTargetMethod("clear");

        return adapter;
    }

}
