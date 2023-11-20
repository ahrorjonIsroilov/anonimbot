package anonim.base.command;

import anonim.base.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Processor extends Command {

    protected Processor(BotService service) {
        super(service);
    }

    public void handleUpdate(Update update) {
        if (beforeHandle(update)) handle(update);
    }

    protected abstract void handle(Update update);
}
