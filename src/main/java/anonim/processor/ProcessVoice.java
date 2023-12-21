package anonim.processor;

import anonim.base.BotService;
import anonim.base.command.Processor;
import anonim.button.InlineButton;
import anonim.entity.Message;
import anonim.entity.auth.AuthUser;
import anonim.entity.session.SessionElement;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.enums.MessageType;
import anonim.util.Utils;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Voice;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

/**
 * @author : Ahrorjon Isroilov
 * @project : Emma
 */
@Component
public class ProcessVoice extends Processor {

    public ProcessVoice(BotService service, SessionUserRepository repository) {
        super(service, repository);
    }

    @Override
    protected void handle(Update update) {
        Voice voice = update.getMessage().getVoice();
        String caption = update.getMessage().getCaption() == null ? "" : update.getMessage().getCaption();
        switch (session.getState(chatId)) {
            case SEND_MESSAGE, ANSWER -> {
                String targetId = session.get(chatId).getTargetId();
                deliverMessage(update, voice, caption, targetId);
            }
//            case ANSWER -> {
//                String targetId = session.get(chatId).getTargetId();
//                deliverMessage(update, voice, caption, targetId);
//            }
            case DEFAULT -> sendMessage(Words.DEFAULT_MESSAGE.get(lang), Formatting.CUSTOM);
        }
    }

    private void deliverMessage(Update update, Voice voice, String caption, String targetId) {
        if (targetId != null) {
            String fileId = voice.getFileId();
            Message message = service.saveMessage(Message.builder()
                .owner(service.getUser(chatId))
                .telegramMessageId(update.getMessage().getMessageId())
                .question(service.existsByMessageId(session.getElement(chatId, SessionElement.QUESTION_ID)) ?
                    service.getMessage((Integer) session.getElement(chatId, SessionElement.QUESTION_ID)) : null)
                .messageType(MessageType.VOICE)
                .fileId(fileId)
                .contentPath(Utils.downloadFile(getFileUrl(fileId), getFilePath(fileId)))
                .build());

            AuthUser user = service.getUser(targetId);
            sendVoice(voice,
                Words.YOU_HAVE_A_NEW_MESSAGE.get(lang).formatted(caption),
                user.getChatId(),
                session.getElement(chatId, SessionElement.QUESTION_ID),
                InlineButton.answer(user.getLanguage(), session.get(chatId).getIdentifier(), message.getTelegramMessageId()));
            sendMessage(Words.MESSAGE_DELIVERED.get(lang).formatted(targetId), Formatting.CUSTOM);
        } else
            sendMessage(Words.SET_TARGET_ID_FIRST.get(lang), new ReplyKeyboardRemove(true), Formatting.CUSTOM);
    }
}
