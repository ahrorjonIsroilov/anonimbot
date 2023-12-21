package anonim.base.command;

import anonim.base.BotService;
import anonim.entity.session.SessionUserRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Processor extends Command {

    protected Processor(BotService service, SessionUserRepository repository) {
        super(service, repository);
    }

    public void handleUpdate(Update update) {
        if (beforeHandle(update)) handle(update);
    }

    protected abstract void handle(Update update);
}
