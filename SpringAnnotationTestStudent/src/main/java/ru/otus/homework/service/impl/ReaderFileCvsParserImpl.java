package ru.otus.homework.service.impl;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.homework.domain.Question;
import ru.otus.homework.service.ReaderFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.homework.utils.Constants.*;

@Service
@PropertySource("classpath:application.properties")
public class ReaderFileCvsParserImpl implements ReaderFile {

    private final String fileName;

    public ReaderFileCvsParserImpl(@Value("${fileName}") String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Question> getQuestions() {
        try (InputStream stream = getStreamFromFile(fileName)) {
            assert stream != null;
            return parsingFile(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private InputStream getStreamFromFile(String fileName) {
        return getClass().getClassLoader().getResourceAsStream(fileName);
    }

    private List<Question> parsingFile(InputStream stream) throws IOException {
        CSVParser csvParser = getCsvParser(stream);
        return csvParser.getRecords()
                .stream()
                .map(record -> new Question(
                        record.get(QUESTION_POSITION),
                        getAnswers(record),
                        record.get(CORRECT_ANSWER_POSITION)))
                .collect(Collectors.toList());
    }

    private CSVParser getCsvParser(InputStream stream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return new CSVParser(bufferedReader, CSVFormat.newFormat(DELIMITER_QUESTION));
    }

    private List<String> getAnswers(CSVRecord record) {
        return Arrays.asList(record.get(LIST_ANSWERS_POSITION).split(DELIMITER_ANSWER));
    }
}
