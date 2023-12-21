package anonim.handler;

import anonim.base.BotService;
import anonim.base.command.CallbackCommand;
import anonim.base.handler.BaseHandler;
import anonim.base.handler.Handler;
import anonim.config.BotConfig;
import anonim.entity.session.SessionUserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class CallbackHandler extends BaseHandler implements Handler {
  public CallbackHandler(BotService service, SessionUserRepository repository) {
    super(service,repository);
  }

  @Override
  @Async
  public void handle(Update update) {
    Optional<CallbackCommand> command =
        callbackCommands.stream()
            .filter(
                o ->
                    update
                        .getCallbackQuery()
                        .getData()
                        .split(BotConfig.DATA_SEPARATOR)[0]
                        .equals(o.data()))
            .findFirst();
    command.ifPresent(value -> value.handleCommand(update));
  }
}
