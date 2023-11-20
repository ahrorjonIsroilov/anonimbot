package anonim.command;


import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.button.InlineButton;
import anonim.config.BotConfig;
import anonim.entity.auth.SessionElement;
import anonim.enums.Formatting;
import anonim.enums.State;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LangCommand extends TextCommand {
    public LangCommand(BotService service) {
        super(service);
    }

    @Override
    protected void handle(Update update) {
        sendMessage(Words.CHOOSE_LANG.get(lang), InlineButton.languages(), Formatting.CUSTOM);
        session.setElement(SessionElement.TELEGRAM_UPDATE, update, chatId);
        session.setState(State.DEFAULT, chatId);
    }

    @Override
    public String getName() {
        return BotConfig.COMMAND_INIT + "lang";
    }
}
