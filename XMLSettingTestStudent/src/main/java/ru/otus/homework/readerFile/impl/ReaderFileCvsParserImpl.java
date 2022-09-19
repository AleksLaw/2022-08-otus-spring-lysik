package ru.otus.homework.readerFile.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import ru.otus.homework.common.Question;
import ru.otus.homework.readerFile.ReaderFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.homework.utils.Constants.*;

public class ReaderFileCvsParserImpl implements ReaderFile {

    private final String fileName;

    public ReaderFileCvsParserImpl(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getQuestions() {

        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            assert stream != null;
            CSVParser csvParser =
                    new CSVParser(
                            new BufferedReader(
                                    new InputStreamReader(stream)), CSVFormat.newFormat(DELIMITER_QUESTION));
            return csvParser
                    .getRecords()
                    .stream()
                    .map(record -> new Question(
                            record.get(QUESTION_POSITION),
                            getAnswers(record),
                            record.get(CORRECT_ANSWER_POSITION)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<String> getAnswers(CSVRecord record) {
        return Arrays.asList(record.get(LIST_ANSWERS_POSITION).split(DELIMITER_ANSWER));
    }
}
