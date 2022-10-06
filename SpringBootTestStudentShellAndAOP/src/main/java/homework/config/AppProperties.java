package homework.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@Getter
@Setter
@ConfigurationProperties(prefix = "properties")
public class AppProperties {
    private int needScoreForPass;
    private Locale locale;
    private String filename;
    private String fileExtension;

    public String getFilename() {
        return filename + locale + fileExtension;
    }
}

