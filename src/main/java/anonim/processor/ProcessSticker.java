package anonim.processor;

import anonim.base.BotService;
import anonim.base.command.Processor;
import anonim.button.InlineButton;
import anonim.entity.Message;
import anonim.entity.auth.AuthUser;
import anonim.entity.auth.SessionElement;
import anonim.enums.Formatting;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;

/**
 * @author : Ahrorjon Isroilov
 * @project : Emma
 */
@Component
public class ProcessSticker extends Processor {
    public ProcessSticker(BotService service) {
        super(service);
    }

    @Override
    protected void handle(Update update) {
        Sticker sticker = update.getMessage().getSticker();
        switch (session.getState(chatId)) {
            case SEND_MESSAGE -> {
                String targetId = session.get(chatId).getTargetId();
                deliverMessage(update, sticker, targetId);
            }
            case ANSWER -> {
                String targetId = session.getElement(chatId, SessionElement.ANSWER_ID);
                deliverMessage(update, sticker, targetId);
            }
            case DEFAULT -> sendMessage(Words.DEFAULT_MESSAGE.get(lang), Formatting.CUSTOM);
        }
    }

    private void deliverMessage(Update update, Sticker sticker, String targetId) {
        if (targetId != null) {
            Message message = Message.builder()
                .owner(service.getUser(chatId))
                .telegramMessageId(update.getMessage().getMessageId())
                .question(service.getMessage((Integer) session.getElement(chatId, SessionElement.QUESTION_ID)) != null ?
                    service.getMessage((Integer) session.getElement(chatId, SessionElement.QUESTION_ID)) : null)
                .build();
            service.saveMessage(message);
            AuthUser usr = service.getUser(targetId);
            sendSticker(sticker.getFileId(),
                usr.getChatId(),
                message.getQuestion() != null ? message.getQuestion().getTelegramMessageId() : null,
                InlineButton.answer(usr.getLanguage(), session.get(chatId).getIdentifier(), message.getTelegramMessageId()));
            sendMessage(Words.MESSAGE_DELIVERED.get(lang).formatted(targetId), Formatting.CUSTOM);
        } else
            sendMessage(Words.SET_TARGET_ID_FIRST.get(lang), new ReplyKeyboardRemove(true), Formatting.CUSTOM);
    }
}
