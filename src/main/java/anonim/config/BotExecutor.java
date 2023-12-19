package anonim.config;

import anonim.base.Bot;
import anonim.base.BotService;
import anonim.entity.session.SessionUserRepository;
import anonim.handler.CallbackHandler;
import anonim.handler.MessageHandler;
import anonim.handler.UpdateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotExecutor {
    public static Bot bot;
    private final BotService service;
    private final SessionUserRepository repository;

    public BotExecutor(BotService service, SessionUserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @Bean
    public void main() {
        TelegramBotsApi api;
        try {
            api = new TelegramBotsApi(DefaultBotSession.class);
            bot =
                new Bot(new UpdateHandler(new MessageHandler(service, repository), new CallbackHandler(service, repository)));
            api.registerBot(bot);
            System.out.println("Connected");
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
