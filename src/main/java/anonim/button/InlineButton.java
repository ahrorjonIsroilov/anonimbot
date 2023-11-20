package anonim.button;

import anonim.config.BotConfig;
import anonim.enums.Language;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InlineButton {
    private static final InlineKeyboardMarkup board = new InlineKeyboardMarkup();

    public static InlineKeyboardMarkup languages() {
        InlineKeyboardButton uz = new InlineKeyboardButton(Language.UZ.getCode());
        uz.setCallbackData(BotConfig.joinCallbackData("lang", Language.UZ.name()));
        InlineKeyboardButton ru = new InlineKeyboardButton(Language.RU.getCode());
        ru.setCallbackData(BotConfig.joinCallbackData("lang", Language.RU.name()));
        InlineKeyboardButton en = new InlineKeyboardButton(Language.EN.getCode());
        en.setCallbackData(BotConfig.joinCallbackData("lang", Language.EN.name()));
        board.setKeyboard(List.of(getRow(uz), getRow(ru), getRow(en)));
        return board;
    }

    public static InlineKeyboardMarkup answer(Language language, String id, Integer messageId) {
        InlineKeyboardButton answer = new InlineKeyboardButton(Words.ANSWER.get(language));
        answer.setCallbackData(BotConfig.joinCallbackData("answer", id, messageId.toString()));
        board.setKeyboard(List.of(getRow(answer)));
        return board;
    }

    public static InlineKeyboardMarkup cancel(Language language) {
        InlineKeyboardButton cancel = new InlineKeyboardButton(Words.CANCEL.get(language));
        cancel.setCallbackData("cancel");
        board.setKeyboard(List.of(getRow(cancel)));
        return board;
    }

    private static List<InlineKeyboardButton> getRow(InlineKeyboardButton... buttons) {
        return Arrays.stream(buttons).collect(Collectors.toList());
    }

}
