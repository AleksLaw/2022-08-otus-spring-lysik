package homework.service.impl;

import homework.domain.Question;
import homework.service.ReaderFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ReaderFileCvsParserImpl implements ReaderFile {
    public static final char DELIMITER_QUESTION = ';';
    public static final String DELIMITER_ANSWER = ":";
    public static final int QUESTION_POSITION = 0;
    public static final int LIST_ANSWERS_POSITION = 1;
    public static final int CORRECT_ANSWER_POSITION = 2;

    @Override
    public List<Question> getQuestions(String fileName) {
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        return new CSVParser(bufferedReader, CSVFormat.newFormat(DELIMITER_QUESTION));
    }

    private List<String> getAnswers(CSVRecord record) {
        return Arrays.asList(record.get(LIST_ANSWERS_POSITION).split(DELIMITER_ANSWER));
    }
}
