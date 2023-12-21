package anonim.config;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class BotConfig {
    public static String BOT_USERNAME ="@anonimqabot";
    public static String BOT_TOKEN = "6003283552:AAHFARB9YGxd6hoZ9RK3GHqjKmT7hyCL2WU";
    public static String TEST_USERNAME = "@testtewfbot";
    public static String TEST_TOKEN = "6525898700:AAGW3rtLURetM519h3GyrjrbuAZcUoJJV00";
    public static Boolean USE_WEBHOOK = false;
    public static String COMMAND_INIT = "/";
    public static String DATA_SEPARATOR = "::";

    public static String joinCallbackData(String... callbacks) {
        StringBuilder sb = new StringBuilder();
        String[] clone = callbacks.clone();
        for (String s : clone) {
            sb.append(s).append(DATA_SEPARATOR);
        }
        String result = sb.toString();
        return result.substring(0, result.length() - 2);
    }
}
