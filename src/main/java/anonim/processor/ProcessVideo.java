package anonim.processor;

import anonim.base.BotService;
import anonim.base.command.Processor;
import anonim.button.InlineButton;
import anonim.entity.Message;
import anonim.entity.auth.AuthUser;
import anonim.entity.session.SessionElement;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.enums.Role;
import anonim.util.Words;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import java.util.List;

import static anonim.enums.State.DEFAULT;

/**
 * @author : Ahrorjon Isroilov
 * @project : Emma
 */
@Component
public class ProcessVideo extends Processor {
    public ProcessVideo(BotService service, SessionUserRepository repository) {
        super(service,repository);
    }

    @Override
    protected void handle(Update update) {
        Video video = update.getMessage().getVideo();
        String caption = update.getMessage().getCaption() == null ? "" : update.getMessage().getCaption();
        switch (session.getState(chatId)) {
            case SEND_MESSAGE -> {
                String targetId = session.get(chatId).getTargetId();
                deliverMessage(update, video, caption, targetId);
            }
            case ANSWER -> {
                String targetId = session.getElement(chatId, SessionElement.ANSWER_ID);
                deliverMessage(update, video, caption, targetId);
            }
            case SEND_ADS -> {
                if (session.getRole(chatId).equals(Role.ADMIN)) {
                    List<AuthUser> users = service.getJoinedUsers();
                    sendAds(users, video, caption, update.getMessage().getCaptionEntities(), update.getMessage().getReplyMarkup());
                    sendMessage("Advertisement sent successfully!", Formatting.CUSTOM);
                    session.setState(DEFAULT, chatId);
                    sendSticker("CAACAgIAAxkBAAECGT9lWwT9DGqlpKhRNkeSG4385JlSSAACwyYAAmBwWUrFigEhiHQdxzME",
                        chatId, null, null);
                }
            }
            case DEFAULT -> sendMessage(Words.DEFAULT_MESSAGE.get(lang), Formatting.CUSTOM);
        }
    }

    @Async
    public void sendAds(List<AuthUser> users, Video video, String caption, List<MessageEntity> entities, InlineKeyboardMarkup markup) {
        for (AuthUser user : users) sendAds(video, caption, user.getChatId(), markup, entities);
    }

    private void deliverMessage(Update update, Video video, String caption, String targetId) {
        if (targetId != null) {
            Message message = Message.builder()
                .owner(service.getUser(chatId))
                .telegramMessageId(update.getMessage().getMessageId())
                .question(service.getMessage((Integer) session.getElement(chatId, SessionElement.QUESTION_ID)) != null ?
                    service.getMessage((Integer) session.getElement(chatId, SessionElement.QUESTION_ID)) : null)
                .build();
            service.saveMessage(message);
            AuthUser usr = service.getUser(targetId);
            sendVideo(video,
                Words.YOU_HAVE_A_NEW_MESSAGE.get(lang).formatted(caption),
                usr.getChatId(),
                message.getQuestion() != null ? message.getQuestion().getTelegramMessageId() : null,
                InlineButton.answer(usr.getLanguage(), session.get(chatId).getIdentifier(), message.getTelegramMessageId()));
            sendMessage(Words.MESSAGE_DELIVERED.get(lang).formatted(targetId), Formatting.CUSTOM);
        } else
            sendMessage(Words.SET_TARGET_ID_FIRST.get(lang), new ReplyKeyboardRemove(true), Formatting.CUSTOM);
    }
}