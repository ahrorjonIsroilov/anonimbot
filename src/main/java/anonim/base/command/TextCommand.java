package anonim.base.command;

import anonim.base.BotService;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class TextCommand extends Command {

    protected TextCommand(BotService service) {
        super(service);
    }

    public void handleCommand(Update update) {
        if (beforeHandle(update)) handle(update);
    }

    protected abstract void handle(Update update);

    public abstract String getName();
}
