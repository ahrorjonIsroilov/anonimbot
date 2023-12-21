package anonim.handler;

import anonim.base.handler.Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class UpdateHandler implements Handler {
    private final MessageHandler messageHandler;
    private final CallbackHandler callbackHandler;

    @Override
    public void handle(Update update) {
        if (update.hasMessage()) messageHandler.handle(update);
        else if (update.hasCallbackQuery()) callbackHandler.handle(update);
    }
}
