package anonim.command.inline;

import anonim.base.BotService;
import anonim.base.command.CallbackCommand;
import anonim.entity.session.SessionElement;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.State;
import anonim.util.Words;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Cancel extends CallbackCommand {
    public Cancel(BotService service, SessionUserRepository repository) {
        super(service,repository);
    }

    @Override
    public String data() {
        return "cancel";
    }

    @Override
    protected void handle(Update update) {
        session.clearElement(SessionElement.QUESTION_ID, chatId);
        session.clearElement(SessionElement.ANSWER_ID, chatId);
        session.setState(State.DEFAULT, chatId);
        sendPopup(Words.CANCELED.get(lang), callbackQuery.getId());
        editMessage(update, Words.CANCELED.get(lang));
        //removeMessageReplyMarkup(update);
    }

    @Override
    public String getName() {
        return "Close";
    }
}
