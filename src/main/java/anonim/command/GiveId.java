package anonim.command;


import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.config.BotConfig;
import anonim.entity.auth.AuthUser;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.enums.Role;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Objects;

@Component
public class GiveId extends TextCommand {
    public GiveId(BotService service, SessionUserRepository repository) {
        super(service,repository);
    }

    @Override
    protected void handle(Update update) {
        if (Objects.requireNonNull(session.getRole(chatId)) == Role.ADMIN) {
            if (update.getMessage().getText().split(" ").length < 3) {
                sendMessage("You are using the command incorrectly\nFormat: <code>/give oldID newID</code>", Formatting.CUSTOM);
                return;
            }
            String oldId = update.getMessage().getText().split(" ")[1];
            String newId = update.getMessage().getText().split(" ")[2];
            if (!service.existByIdentifier(oldId)) {
                sendMessage("User not found with %s".formatted(oldId), Formatting.CUSTOM);
                return;
            }
            if (service.existUser(newId)) {
                sendMessage("User already exists with newID", Formatting.CUSTOM);
                return;
            }
            AuthUser user = service.getUser(oldId);
            user.setIdentifier(newId);
            session.get(user.getChatId()).setIdentifier(newId);
            service.saveUser(user);
            sendMessage("Success", Formatting.BOLD);
            sendMessage(Words.ID_CHANGED.get(user.getLanguage()).formatted(newId), user.getChatId(), Formatting.CUSTOM);
        }
    }

    @Override
    public String getName() {
        return BotConfig.COMMAND_INIT + "give";
    }
}
