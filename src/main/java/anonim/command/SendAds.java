package anonim.command;


import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.config.BotConfig;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.enums.Role;
import anonim.enums.State;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class SendAds extends TextCommand {
    public SendAds(BotService service, SessionUserRepository repository) {
        super(service,repository);
    }

    @Override
    protected void handle(Update update) {
        if (Objects.requireNonNull(session.getRole(chatId)) == Role.ADMIN) {
            session.setState(State.SEND_ADS, chatId);
            sendMessage("Forward the advertisement post", Formatting.BOLD);
        }
    }

    @Override
    public String getName() {
        return BotConfig.COMMAND_INIT + "ads";
    }
}
