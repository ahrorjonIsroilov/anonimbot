package anonim.base.command;

import anonim.base.BotService;
import anonim.entity.auth.AuthUser;
import anonim.entity.session.Session;
import anonim.entity.session.SessionElement;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.enums.Language;
import anonim.enums.Role;
import anonim.enums.State;
import anonim.util.Utils;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static anonim.config.BotExecutor.bot;

public abstract class Command {

    protected final BotService service;
    protected final Session session;
    protected Long chatId;
    protected Language lang = Language.EN;
    protected ExecutorService thread;

    protected Command(BotService service, SessionUserRepository repository) {
        this.service = service;
        this.session = Session.build(repository);
        thread = Executors.newFixedThreadPool(4);
    }

    protected boolean beforeHandle(Update update) {
        chatId = getMessage(update).getChatId();
        if (session.existAndJoined(chatId)) {
            lang = session.get(chatId).getLanguage();
            return true;
        } else {
            AuthUser user = service.getUser(chatId);
            if (Objects.nonNull(user) && user.isJoined()) {
                session.set(session.prepare(user));
                lang = session.get(chatId).getLanguage();
                session.setElement(SessionElement.QUESTION_ID, 0, chatId);
                return true;
            } else {
                AuthUser authUser = AuthUser.builder()
                    .role(Role.USER)
                    .state(State.DEFAULT)
                    .blocked(false)
                    .identifier(Utils.generateIdentifier())
                    .joined(true)
                    .language(Language.UZ)
                    .username(update.getMessage().getFrom().getUserName())
                    .chatId(chatId)
                    .build();
                service.saveUser(authUser);
                session.set(session.prepare(authUser));
                session.setElement(SessionElement.QUESTION_ID, 0, chatId);
            }
        }
        lang = session.get(chatId).getLanguage();
        return true;
    }

    protected void sendMessage(String text, Formatting formatting) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableHtml(true);
        text = formatText(text, formatting);
        message.setText(text);
        try {
            bot.execute(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void sendPhoto(
        PhotoSize photoSize, String caption, Long chatId, Integer replyToMessageId, InlineKeyboardMarkup markup) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(photoSize.getFileId()));
        sendPhoto.setChatId(chatId);
        sendPhoto.setReplyMarkup(markup);
        sendPhoto.setReplyToMessageId(replyToMessageId);
        sendPhoto.setCaption(caption);
        sendPhoto.setParseMode("HTML");
        try {
            bot.execute(sendPhoto);
        } catch (TelegramApiException e) {
            sendPhoto.setReplyToMessageId(null);
            try {
                bot.execute(sendPhoto);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void sendVideo(
        Video video, String caption, Long chatId, Integer replyToMessageId, InlineKeyboardMarkup markup) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setVideo(new InputFile(video.getFileId()));
        sendVideo.setChatId(chatId);
        sendVideo.setReplyMarkup(markup);
        sendVideo.setReplyToMessageId(replyToMessageId);
        sendVideo.setCaption(caption);
        sendVideo.setParseMode("HTML");
        try {
            bot.execute(sendVideo);
        } catch (TelegramApiException e) {
            sendVideo.setReplyToMessageId(null);
            try {
                bot.execute(sendVideo);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void sendVideoNote(
        VideoNote videoNote, Long chatId, Integer replyToMessageId, InlineKeyboardMarkup markup) {
        SendVideoNote sendVideoNote = new SendVideoNote();
        sendVideoNote.setVideoNote(new InputFile(videoNote.getFileId()));
        sendVideoNote.setChatId(chatId);
        sendVideoNote.setReplyMarkup(markup);
        sendVideoNote.setReplyToMessageId(replyToMessageId);
        try {
            bot.execute(sendVideoNote);
        } catch (TelegramApiException e) {
            sendVideoNote.setReplyToMessageId(null);
            try {
                bot.execute(sendVideoNote);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void sendAudio(
        Audio audio, String caption, Long chatId, Integer replyToMessageId, InlineKeyboardMarkup markup) {
        SendAudio sendAudio = new SendAudio();
        sendAudio.setAudio(new InputFile(audio.getFileId()));
        sendAudio.setChatId(chatId);
        sendAudio.setReplyMarkup(markup);
        sendAudio.setReplyToMessageId(replyToMessageId);
        sendAudio.setCaption(caption);
        sendAudio.setParseMode("HTML");
        try {
            bot.execute(sendAudio);
        } catch (TelegramApiException e) {
            sendAudio.setReplyToMessageId(null);
            try {
                bot.execute(sendAudio);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void sendVoice(
        Voice voice, String caption, Long chatId, Integer replyToMessageId, InlineKeyboardMarkup markup) {
        SendVoice sendVoice = new SendVoice();
        sendVoice.setVoice(new InputFile(voice.getFileId()));
        sendVoice.setChatId(chatId);
        sendVoice.setReplyMarkup(markup);
        sendVoice.setReplyToMessageId(replyToMessageId);
        sendVoice.setCaption(caption);
        sendVoice.setParseMode("HTML");
        try {
            bot.execute(sendVoice);
        } catch (TelegramApiException e) {
            sendVoice.setReplyToMessageId(null);
            try {
                bot.execute(sendVoice);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void sendDocument(
        Document document, String caption, Long chatId, Integer replyToMessageId, InlineKeyboardMarkup markupButton) {
        SendDocument doc = new SendDocument();
        doc.setCaption(caption);
        doc.setChatId(chatId);
        doc.setParseMode("HTML");
        doc.setReplyToMessageId(replyToMessageId);
        doc.setReplyMarkup(markupButton);
        doc.setDocument(new InputFile(document.getFileId()));
        try {
            bot.execute(doc);
        } catch (TelegramApiException e) {
            doc.setReplyToMessageId(null);
            try {
                bot.execute(doc);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void sendMessage(
        String text, Long anotherChatId, ReplyKeyboardRemove remove, Formatting formatting) {
        SendMessage message = new SendMessage();
        message.setChatId(anotherChatId);
        message.enableHtml(true);
        text = formatText(text, formatting);
        message.setText(text);
        message.setReplyMarkup(remove);
        try {
            bot.execute(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void sendMessage(String text, Long anotherChatId, Formatting formatting) {
        SendMessage message = new SendMessage();
        message.setChatId(anotherChatId);
        message.enableHtml(true);
        text = formatText(text, formatting);
        message.setText(text);
        try {
            bot.execute(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void sendAds(String text, Long anotherChatId, InlineKeyboardMarkup markup, List<MessageEntity> entities) {
        SendMessage message = new SendMessage();
        message.setChatId(anotherChatId);
        message.setText(text);
        message.setEntities(entities);
        message.setReplyMarkup(markup);
        try {
            bot.execute(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void sendAds(PhotoSize photoSize, String caption, Long anotherChatId, InlineKeyboardMarkup markup, List<MessageEntity> entities) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(anotherChatId);
        sendPhoto.setCaption(caption);
        sendPhoto.setCaptionEntities(entities);
        sendPhoto.setReplyMarkup(markup);
        sendPhoto.setPhoto(new InputFile(photoSize.getFileId()));
        try {
            bot.execute(sendPhoto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void sendAds(Video video, String caption, Long anotherChatId, InlineKeyboardMarkup markup, List<MessageEntity> entities) {
        SendVideo sendVideo = new SendVideo();
        sendVideo.setChatId(anotherChatId);
        sendVideo.setCaption(caption);
        sendVideo.setCaptionEntities(entities);
        sendVideo.setReplyMarkup(markup);
        sendVideo.setVideo(new InputFile(video.getFileId()));
        try {
            bot.execute(sendVideo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void sendMessage(String text, ReplyKeyboardMarkup markup, Formatting formatting) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableHtml(true);
        message.setReplyMarkup(markup);
        text = formatText(text, formatting);
        message.setText(text);
        try {
            bot.execute(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void sendMessage(String text, InlineKeyboardMarkup markup, Formatting formatting) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableHtml(true);
        message.setReplyMarkup(markup);
        text = formatText(text, formatting);
        message.setText(text);
        try {
            bot.execute(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void sendMessage(
        String text, Long anotherChatId, Integer replyToMessageId, InlineKeyboardMarkup markup, Formatting formatting) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setChatId(anotherChatId);
        message.enableHtml(true);
        message.setReplyMarkup(markup);
        message.setReplyToMessageId(replyToMessageId);
        text = formatText(text, formatting);
        message.setText(text);
        try {
            bot.execute(message);
        } catch (Exception e) {
            message.setReplyToMessageId(null);
            try {
                bot.execute(message);
            } catch (TelegramApiException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    protected void sendMessage(
        String text, ReplyKeyboardRemove keyboardRemove, Formatting formatting) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.enableHtml(true);
        message.setReplyMarkup(keyboardRemove);
        text = formatText(text, formatting);
        message.setText(text);
        try {
            bot.execute(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    protected void editMessage(
        Update update, String text, InlineKeyboardMarkup markup, Formatting formatting) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        Integer messageId = getMessage(update).getMessageId();
        text = formatText(text, formatting);
        EditMessageText editMessage = new EditMessageText(text);
        editMessage.setMessageId(messageId);
        editMessage.setChatId(chatId);
        editMessage.enableHtml(true);
        editMessage.setReplyMarkup(markup);
        try {
            bot.execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void editMessage(Update update, String text, Formatting formatting) {
        SendMessage message = new SendMessage();
        Integer messageId = getMessage(update).getMessageId();
        message.setChatId(chatId);
        text = formatText(text, formatting);
        EditMessageText editMessage = new EditMessageText(text);
        editMessage.setMessageId(messageId);
        editMessage.setEntities(getMessage(update).getEntities());
        editMessage.setChatId(chatId);
        editMessage.enableHtml(true);
        try {
            bot.execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void editMessage(Update update, String text) {
        Integer messageId = getMessage(update).getMessageId();
        EditMessageText editMessage = new EditMessageText();
        editMessage.setText(text);
        editMessage.setParseMode("HTML");
        editMessage.setMessageId(messageId);
        editMessage.setReplyMarkup(null);
        editMessage.setChatId(chatId);
        try {
            bot.execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void removeMessageReplyMarkup(Update update) {
        Integer messageId = getMessage(update).getMessageId();
        EditMessageReplyMarkup editMessage = new EditMessageReplyMarkup();
        editMessage.setMessageId(messageId);
        editMessage.setReplyMarkup(null);
        editMessage.setChatId(chatId);
        try {
            bot.execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    protected void editMessage(Update update, String text, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage();
        Integer messageId = getMessage(update).getMessageId();
        message.setChatId(chatId);
        EditMessageText editMessage = new EditMessageText(text);
        editMessage.setMessageId(messageId);
        editMessage.setChatId(chatId);
        editMessage.setEntities(getMessage(update).getEntities());
        editMessage.setReplyMarkup(markup);
        try {
            bot.execute(editMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void sendSticker(String fileId, Long innerChatId, Integer replyToMessageId, InlineKeyboardMarkup markup) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setChatId(innerChatId);
        sendSticker.setReplyMarkup(markup);
        sendSticker.setReplyToMessageId(replyToMessageId);
        sendSticker.setSticker(new InputFile(fileId));
        try {
            bot.execute(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void sendPopup(String text, String callbackQueryId) {
        AnswerCallbackQuery query = new AnswerCallbackQuery();
        query.setText(text);
        query.setCallbackQueryId(callbackQueryId);
        try {
            bot.execute(query);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void sendPopup(String text, String callbackQueryId, Boolean showAlert) {
        AnswerCallbackQuery query = new AnswerCallbackQuery();
        query.setText(text);
        query.setCallbackQueryId(callbackQueryId);
        query.setShowAlert(showAlert);
        try {
            bot.execute(query);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    protected void deleteMessage(Update update) {
        try {
            bot.execute(new DeleteMessage(chatId.toString(), getMessage(update).getMessageId()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private Message getMessage(Update update) {
        if (update.hasMessage()) return update.getMessage();
        else if (update.hasCallbackQuery()) return update.getCallbackQuery().getMessage();
        return new Message();
    }

    private String formatText(String text, Formatting formatting) {
        switch (formatting) {
            case BOLD -> text = "<b>" + text + "</b>";
            case ITALIC -> text = "<i>" + text + "</i>";
            case STRIKE -> text = "<s>" + text + "</s>";
            case UNDERLINE -> text = "<u>" + text + "</u>";
            case SPOILER -> text = "<span class=\"tg-spoiler\">" + text + "</span>";
            case MONOSPACE -> text = "<code>" + text + "</code>";
        }
        return text;
    }

    protected String getFilePath(String fileId) {
        GetFile getFile = new GetFile();
        getFile.setFileId(fileId);
        File file = new File();
        try {
            file = bot.execute(getFile);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return file.getFilePath();
    }
}
