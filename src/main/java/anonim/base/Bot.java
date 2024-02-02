package anonim.base;

import anonim.config.BotConfig;
import anonim.handler.UpdateHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Bot extends TelegramLongPollingBot {
    private final UpdateHandler handler;

    public Bot(UpdateHandler handler) {
        super(BotConfig.BOT_TOKEN);
        this.handler = handler;
    }

    @Override
    public String getBotUsername() {
        return BotConfig.BOT_USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        handler.handle(update);
    }
}
