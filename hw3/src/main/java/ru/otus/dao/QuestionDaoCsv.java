package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.springframework.stereotype.Component;
import ru.otus.config.CsvResourceName;
import ru.otus.domain.Answer;
import ru.otus.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class QuestionDaoCsv implements QuestionDao {
    private static final char DELIMITER = ';';

    private static final String CORRECT_ANSWER_DELIMITER = ":";

    private final CsvResourceName csvResourceName;

    @Override
    public List<Question> getQuestions() {
        String csvResource = csvResourceName.getCsvResourceName();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(csvResource)), StandardCharsets.UTF_8))) {
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.newFormat(DELIMITER));
            List<Question> questions = new ArrayList<>();
            csvParser.getRecords().forEach(csvRecord -> {
                List<Answer> answers = new ArrayList<>();
                Question question = new Question(csvRecord.get(0), answers);
                IntStream.range(1, csvRecord.size()).mapToObj(i -> csvRecord.get(i).split(CORRECT_ANSWER_DELIMITER))
                        .map(s -> new Answer(s[0], Boolean.parseBoolean(s[1]))).forEach(answers::add);
                questions.add(question);
            });
            return questions;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}