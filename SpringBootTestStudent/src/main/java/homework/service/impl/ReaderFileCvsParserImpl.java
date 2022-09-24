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

import static homework.utils.Constants.*;


@Service
public class ReaderFileCvsParserImpl implements ReaderFile {
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
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return new CSVParser(bufferedReader, CSVFormat.newFormat(DELIMITER_QUESTION));
    }

    private List<String> getAnswers(CSVRecord record) {
        return Arrays.asList(record.get(LIST_ANSWERS_POSITION).split(DELIMITER_ANSWER));
    }
}
