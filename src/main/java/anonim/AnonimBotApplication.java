package anonim;

import anonim.config.BotExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class AnonimBotApplication implements CommandLineRunner {
    private final BotExecutor executor;

    public static void main(String[] args) {
        SpringApplication.run(AnonimBotApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        executor.main();
    }
}
