package anonim.handler;

import anonim.base.handler.Handler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class UpdateHandler implements Handler {
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    public UpdateHandler(
        MessageHandler messageHandler,
        CallbackHandler callbackHandler) {
        this.messageHandler = messageHandler;
        this.callbackHandler = callbackHandler;
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage()) messageHandler.handle(update);
        else if (update.hasCallbackQuery()) callbackHandler.handle(update);
    }
}
