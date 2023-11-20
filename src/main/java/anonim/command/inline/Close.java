package anonim.command.inline;

import anonim.base.BotService;
import anonim.base.command.CallbackCommand;
import anonim.entity.auth.SessionElement;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Close extends CallbackCommand {
    public Close(BotService service) {
        super(service);
    }

    @Override
    public String data() {
        return "close";
    }

    @Override
    protected void handle(Update update) {
        session.clearElement(SessionElement.QUESTION_ID, chatId);
        removeMessageReplyMarkup(update);
    }

    @Override
    public String getName() {
        return "Close";
    }
}
