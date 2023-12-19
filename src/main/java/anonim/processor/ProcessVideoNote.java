package anonim.processor;

import anonim.base.BotService;
import anonim.base.command.Processor;
import anonim.button.InlineButton;
import anonim.entity.Message;
import anonim.entity.auth.AuthUser;
import anonim.entity.session.SessionElement;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.VideoNote;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

/**
 * @author : Ahrorjon Isroilov
 * @project : Emma
 */
@Component
public class ProcessVideoNote extends Processor {
    public ProcessVideoNote(BotService service, SessionUserRepository repository) {
        super(service,repository);
    }

    @Override
    protected void handle(Update update) {
        VideoNote videoNote = update.getMessage().getVideoNote();
        switch (session.getState(chatId)) {
            case SEND_MESSAGE -> {
                String targetId = session.get(chatId).getTargetId();
                deliverMessage(update, videoNote, targetId);
            }
            case ANSWER -> {
                String targetId = session.getElement(chatId, SessionElement.ANSWER_ID);
                deliverMessage(update, videoNote, targetId);
            }
            case DEFAULT -> sendMessage(Words.DEFAULT_MESSAGE.get(lang), Formatting.CUSTOM);
        }
    }

    private void deliverMessage(Update update, VideoNote videoNote, String targetId) {
        if (targetId != null) {
            Message message = Message.builder()
                .owner(service.getUser(chatId))
                .telegramMessageId(update.getMessage().getMessageId())
                .question(service.getMessage((Integer) session.getElement(chatId, SessionElement.QUESTION_ID)) != null ?
                    service.getMessage((Integer) session.getElement(chatId, SessionElement.QUESTION_ID)) : null)
                .build();
            service.saveMessage(message);
            AuthUser usr = service.getUser(targetId);
            sendVideoNote(videoNote,
                usr.getChatId(),
                message.getQuestion() != null ? message.getQuestion().getTelegramMessageId() : null,
                InlineButton.answer(usr.getLanguage(), session.get(chatId).getIdentifier(), message.getTelegramMessageId()));
            sendMessage(Words.MESSAGE_DELIVERED.get(lang).formatted(targetId), Formatting.CUSTOM);
        } else
            sendMessage(Words.SET_TARGET_ID_FIRST.get(lang), new ReplyKeyboardRemove(true), Formatting.CUSTOM);
    }
}
