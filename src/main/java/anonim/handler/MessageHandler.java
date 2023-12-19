package anonim.handler;

import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.base.handler.BaseHandler;
import anonim.base.handler.Handler;
import anonim.command.Default;
import anonim.entity.session.SessionUserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class MessageHandler extends BaseHandler implements Handler {
    public MessageHandler(BotService service, SessionUserRepository repository) {
        super(service,repository);
    }

    @Override
    @Async
    public void handle(Update update) {
        String text = update.getMessage().getText();
        Default defaultCommand = (Default) textCommands.get(textCommands.size() - 1);
        if (update.getMessage().hasText()) {
            Optional<TextCommand> command =
                textCommands.stream().filter(o -> text.startsWith(o.getName())).findFirst();
            if (command.isPresent()) {
                command.get().handleCommand(update);
            } else defaultCommand.handleCommand(update);
        } else if (update.getMessage().hasDocument()) processors.get("document").handleUpdate(update);
        else if (update.getMessage().hasPhoto()) processors.get("photo").handleUpdate(update);
        else if (update.getMessage().hasVideo()) processors.get("video").handleUpdate(update);
        else if (update.getMessage().hasAudio()) processors.get("audio").handleUpdate(update);
        else if (update.getMessage().hasVoice()) processors.get("voice").handleUpdate(update);
        else if (update.getMessage().hasVideoNote()) processors.get("videoNote").handleUpdate(update);
        else if (update.getMessage().hasSticker()) processors.get("sticker").handleUpdate(update);
    }
}
