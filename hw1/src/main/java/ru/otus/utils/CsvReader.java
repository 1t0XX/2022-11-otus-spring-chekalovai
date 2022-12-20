package ru.otus.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import ru.otus.dao.QuestionDao;
import ru.otus.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CsvReader implements QuestionDao {

    private static final char DELIMITER = ';';
    private static final String ANSWER_DELIMITER = ":";


    private final String fileName;

    public CsvReader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getQuestions() {

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName)), StandardCharsets.UTF_8)
        )) {
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.newFormat(DELIMITER));
            return csvParser.getRecords().stream().map(csvRecord -> new Question(csvRecord.get(0),
                    Arrays.asList(csvRecord.get(1).split(ANSWER_DELIMITER)),
                    csvRecord.get(2))).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

