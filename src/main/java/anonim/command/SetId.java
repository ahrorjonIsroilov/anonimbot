package anonim.command;


import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.config.BotConfig;
import anonim.entity.auth.AuthUser;
import anonim.enums.Formatting;
import anonim.enums.State;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class SetId extends TextCommand {
    public SetId(BotService service) {
        super(service);
    }

    @Override
    protected void handle(Update update) {
        String[] s = update.getMessage().getText().split(" ");
        if (s.length > 1) {
            String id = s[1];
            if (service.existByIdentifier(id)) {
                session.get(chatId).setTargetId(id);
                AuthUser user = service.getUser(chatId);
                user.setTargetId(id);
                service.saveUser(user);
                sendMessage(Words.WRITE_QUESTION.get(lang), Formatting.CUSTOM);
                session.setState(State.SEND_MESSAGE, chatId);
            } else
                sendMessage(Words.USER_DOEST_NOT_EXISTS_WITH_THIS_ID.get(lang), Formatting.CUSTOM);
        } else {
            sendMessage(Words.ENTER_IDENTIFIER.get(lang), Formatting.CUSTOM);
            session.setState(State.SET_ID, chatId);
        }
    }

    @Override
    public String getName() {
        return BotConfig.COMMAND_INIT + "set";
    }
}
