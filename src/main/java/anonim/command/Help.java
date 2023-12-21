package anonim.command;


import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.config.BotConfig;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.enums.State;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Help extends TextCommand {
    public Help(BotService service, SessionUserRepository repository) {
        super(service,repository);
    }

    @Override
    protected void handle(Update update) {
       sendMessage("https://telegra.ph/A-guide-on-how-to-use-the-Anonymous-QA-bot-02-21",Formatting.CUSTOM);
        session.setState(State.DEFAULT, chatId);
    }

    @Override
    public String getName() {
        return BotConfig.COMMAND_INIT + "help";
    }
}
