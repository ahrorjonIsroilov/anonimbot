package anonim.command.inline;

import anonim.base.BotService;
import anonim.base.command.CallbackCommand;
import anonim.button.InlineButton;
import anonim.config.BotConfig;
import anonim.entity.auth.SessionElement;
import anonim.entity.auth.SessionUser;
import anonim.enums.Formatting;
import anonim.enums.State;
import anonim.util.Words;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Answer extends CallbackCommand {
    public Answer(BotService service) {
        super(service);
    }

    @Override
    public String data() {
        return "answer";
    }

    @Override
    protected void handle(Update update) {
        String targetId = callbackQuery.getData().split(BotConfig.DATA_SEPARATOR)[1];
        Integer messageId = Integer.parseInt(callbackQuery.getData().split(BotConfig.DATA_SEPARATOR)[2]);

        SessionUser sessionUser = session.get(chatId);
        sessionUser.setState(State.ANSWER);
        sessionUser.setTargetId(targetId);
        session.setElement(SessionElement.ANSWER_ID, targetId, chatId);
        session.setElement(SessionElement.QUESTION_ID, messageId, chatId);
        session.set(chatId, sessionUser);
        //removeMessageReplyMarkup(update);
        sendMessage(Words.ENTER_MESSAGE.get(lang), InlineButton.cancel(lang), Formatting.CUSTOM);
    }

    @Override
    public String getName() {
        return "Answer";
    }
}
