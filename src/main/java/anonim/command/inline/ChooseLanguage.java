package anonim.command.inline;

import anonim.base.BotService;
import anonim.base.command.CallbackCommand;
import anonim.config.BotConfig;
import anonim.entity.auth.AuthUser;
import anonim.entity.session.SessionElement;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Language;
import anonim.util.Words;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChooseLanguage extends CallbackCommand {
    public ChooseLanguage(BotService service, SessionUserRepository repository) {
        super(service, repository);
    }

    @Override
    public String data() {
        return "lang";
    }

    @Override
    protected void handle(Update update) {
        String langCode = callbackQuery.getData().split(BotConfig.DATA_SEPARATOR)[1];
        session.get(chatId).setLanguage(Language.valueOf(langCode));
        AuthUser user = service.getUser(chatId);
        user.setLanguage(Language.valueOf(langCode));
        service.saveUser(user);
        sendPopup(Words.LANGUAGE_SET.get(user.getLanguage()), callbackQuery.getId());
        deleteMessage(getMessage(update).getMessageId());
        deleteMessage(session.getElement(chatId, SessionElement.MESSAGE_ID));
        session.clearElement(SessionElement.MESSAGE_ID, chatId);
    }

    @Override
    public String getName() {
        return "Choose language";
    }
}
