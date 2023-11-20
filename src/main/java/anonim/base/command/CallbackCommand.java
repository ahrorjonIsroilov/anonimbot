package anonim.base.command;

import anonim.base.BotService;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class CallbackCommand extends Command {

  protected CallbackQuery callbackQuery;

  protected CallbackCommand(BotService service) {
    super(service);
  }

  public abstract String data();

  public void handleCommand(Update update) {
    callbackQuery = update.getCallbackQuery();
    if (beforeHandle(update)) handle(update);
  }

  protected abstract void handle(Update update);

  public abstract String getName();
}
