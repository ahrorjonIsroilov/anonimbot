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
public class Start extends TextCommand {
    public Start(BotService service) {
        super(service);
    }

    @Override
    protected void handle(Update update) {
        boolean hasChat = update.getMessage().getText().split(" ").length > 1;
        switch (session.getRole(chatId)) {
            case USER -> {
                if (!hasChat) {
                    //sendMessage(Words.HELLO.get(lang), Formatting.CUSTOM);
                    sendMessage(Words.YOUR_LINK.get(lang).formatted(service.getUser(chatId).getIdentifier()), Formatting.CUSTOM);
                    session.setState(State.DEFAULT, chatId);
                } else {
                    String identifier = update.getMessage().getText().split(" ")[1];
                    session.get(chatId).setTargetId(identifier);
                    AuthUser user = service.getUser(chatId);
                    user.setTargetId(identifier);
                    service.saveUser(user);
                    sendMessage(Words.WRITE_QUESTION.get(lang), Formatting.CUSTOM);
                    session.setState(State.SEND_MESSAGE, chatId);
                }
            }
            case ADMIN -> {

            }
        }
    }

    @Override
    public String getName() {
        return BotConfig.COMMAND_INIT + "start";
    }
}
