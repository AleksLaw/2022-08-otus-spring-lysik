package homework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix = "properties")
public class AppProperties {
    private int needScoreForPass;
    private Locale locale;

    public int getNeedScoreForPass() {
        return needScoreForPass;
    }

    public void setNeedScoreForPass(int needScoreForPass) {
        this.needScoreForPass = needScoreForPass;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
