package anonim.command;


import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.button.InlineButton;
import anonim.entity.Message;
import anonim.entity.auth.AuthUser;
import anonim.entity.session.SessionElement;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.enums.MessageType;
import anonim.enums.Role;
import anonim.enums.State;
import anonim.util.Words;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;

@Component
public class Default extends TextCommand {
    public Default(BotService service, SessionUserRepository repository) {
        super(service, repository);
    }

    @Override
    protected void handle(Update update) {
        String targetIdentifier = session.get(chatId).getTargetId();
        switch (session.getState(chatId)) {
            case DEFAULT -> sendMessage(Words.DEFAULT_MESSAGE.get(lang), Formatting.CUSTOM);
            case SEND_MESSAGE, ANSWER -> deliverMessage(update, service.getUser(targetIdentifier).getChatId());
            case SET_ID -> {
                String id = update.getMessage().getText();
                if (service.existByIdentifier(id)) {
                    session.get(chatId).setTargetId(id);
                    AuthUser user = service.getUser(chatId);
                    user.setTargetId(id);
                    service.saveUser(user);
                    sendMessage(Words.WRITE_QUESTION.get(lang), InlineButton.cancel(lang), Formatting.CUSTOM);
                    session.setState(State.SEND_MESSAGE, chatId);
                } else {
                    sendMessage(Words.USER_DOEST_NOT_EXISTS_WITH_THIS_ID.get(lang), Formatting.CUSTOM);
                    session.setState(State.DEFAULT, chatId);

                }
            }
            case SEND_ADS -> {
                if (session.getRole(chatId).equals(Role.ADMIN)) {
                    List<AuthUser> users = service.getJoinedUsers();
                    String text = update.getMessage().getText();
                    sendAds(users, text, update.getMessage().getEntities(), update.getMessage().getReplyMarkup());
                    sendMessage("Sent!", Formatting.CUSTOM);
                    session.setState(State.DEFAULT, chatId);
                }
            }
        }
    }

    @Async
    public void sendAds(List<AuthUser> users, String text, List<MessageEntity> entities, InlineKeyboardMarkup markup) {
        for (AuthUser user : users) sendAds(text, user.getChatId(), markup, entities);
    }

    private void deliverMessage(Update update, Long targetId) {
        if (targetId != null) {
            String text = update.getMessage().getText();
            Message message = service.saveMessage(Message.builder()
                .owner(service.getUser(chatId))
                .telegramMessageId(update.getMessage().getMessageId())
                .question(service.existsByMessageId(session.getElement(chatId, SessionElement.QUESTION_ID)) ?
                    service.getMessage((Integer) session.getElement(chatId, SessionElement.QUESTION_ID)) : null)
                .messageType(MessageType.TEXT)
                .content(text)
                .build());

            AuthUser user = service.getUser(targetId);
            sendMessage(Words.YOU_HAVE_A_NEW_MESSAGE.get(user.getLanguage()).formatted(text),
                user.getChatId(),
                session.getElement(chatId, SessionElement.QUESTION_ID),
                InlineButton.answer(user.getLanguage(), session.get(chatId).getIdentifier(), message.getTelegramMessageId()),
                Formatting.CUSTOM);
            sendMessage(Words.MESSAGE_DELIVERED.get(lang), Formatting.CUSTOM);
        } else
            sendMessage(Words.SET_TARGET_ID_FIRST.get(lang), Formatting.CUSTOM);
        session.setState(State.DEFAULT, chatId);
    }

    @Override
    public String getName() {
        return "default";
    }
}
