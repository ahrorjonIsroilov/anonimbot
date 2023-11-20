package anonim.config;

import anonim.base.Bot;
import anonim.base.BotService;
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

    public BotExecutor(BotService service) {
        this.service = service;
    }

    @Bean
    public void main() {
        TelegramBotsApi api;
        try {
            api = new TelegramBotsApi(DefaultBotSession.class);
            bot =
                new Bot(new UpdateHandler(new MessageHandler(service), new CallbackHandler(service)));
            api.registerBot(bot);
            System.out.println("Connected");
        } catch (TelegramApiException e) {
            System.out.println(e.getMessage());
        }
    }
}
