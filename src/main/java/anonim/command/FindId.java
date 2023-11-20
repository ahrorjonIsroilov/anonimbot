package anonim.command;


import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.config.BotConfig;
import anonim.enums.Formatting;
import anonim.enums.State;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class FindId extends TextCommand {
    public FindId(BotService service) {
        super(service);
    }

    @Override
    protected void handle(Update update) {
        String identifier = session.get(chatId).getIdentifier();
        sendMessage(Words.MY_ID.get(lang).formatted(identifier, identifier), Formatting.CUSTOM);
        session.setState(State.DEFAULT, chatId);
    }

    @Override
    public String getName() {
        return BotConfig.COMMAND_INIT + "id";
    }
}
