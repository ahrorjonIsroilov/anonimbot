package anonim.util;


import anonim.enums.Language;
import lombok.RequiredArgsConstructor;

/**
 * @author : Ahrorjon Isroilov
 * @project : Emma
 */
@RequiredArgsConstructor
public enum Words {
    HELLO(
        """
            🇺🇿 Botdan foydanalanishni bo'yicha qo'llanma
                    
            https://telegra.ph/A-guide-on-how-to-use-the-Anonymous-QA-bot-02-21""",
        """
            🇷🇺 Руководство по использованию бота
                    
            https://telegra.ph/A-guide-on-how-to-use-the-Anonymous-QA-bot-02-21""",
        """
            🇬🇧 A guide to using the bot
                    
            https://telegra.ph/A-guide-on-how-to-use-the-Anonymous-QA-bot-02-21"""),
    IDENTIFIER_SET("✅", "✅", "✅"),
    WRITE_QUESTION("<b>Anonim xabaringizni yuboring:</b>", "<b>Напиши анонимный сообщение:</b>", "<b>Write an anonymous message:</b>"),
    ENTER_MESSAGE("<b>Yuborish uchun xabarni kiriting</b>",
        "<b>Введите сообщение для отправки</b>",
        "<b>Enter a message to send</b>"),
    USER_DOEST_NOT_EXISTS_WITH_THIS_ID("<b>Bu IDga ega foydalanuvchi mavjud emas 😶</b>",
        "<b>Пользователь с таким ID не существует 😶</b>",
        "<b>User with this ID does not exist 😶</b>"),
    ENTER_IDENTIFIER("<b>Xabar yubormoqchi bo'lgan foydalanuvchining IDsini kiriting</b>",
        "<b>Введите идентификатор пользователя, которому вы хотите отправить сообщение</b>",
        "<b>Enter the ID of the user you want to send a message to</b>"),
    MESSAGE_DELIVERED("<b>✅ Xabaringiz yetkazildi!</b>",
        "<b>✅ Сообщение доставлено</b>",
        "<b>✅ Message delivered</b>"),
    SET_TARGET_ID_FIRST("Xabar yuborishdan oldin foydalanuvchi IDsini kiriting\n\n /set userID",
        "Пожалуйста, введите идентификатор пользователя перед отправкой сообщения\n\n /set userID",
        "Please enter a user ID before sending a message\n\n /set userID"),
    CHOOSE_LANG("Tilni tanlang",
        "Выберите язык",
        "Select a language"),
    USER_NOT_FOUND("😕 <b>Foydalanuvchi topilmadi</b>",
        "😕 <b>Пользователь не найден</b>",
        "😕 <b>User not found</b>"),
    LANGUAGE_SET("✅ Til o'zgartirildi",
        "✅ Язык был изменен",
        "✅ Language updated"),
    ANSWER("💬 Javob berish",
        "💬 Отвечать",
        "💬 Answer"),

    CANCEL("Bekor qilish",
        "Отменить",
        "Cancel"),
    YOU_HAVE_A_NEW_MESSAGE("<b>Sizda yangi xabar bor:</b>\n\n<i>%s</i>",
        "<b>У вас есть новое сообщение:</b>\n\n<i>%s</i>",
        "<b>You have a new message:</b>\n\n<i>%s</i>"),
    ID_CHANGED("ID o'zgartirildi: <code>%s</code>",
        "Ваш идентификатор был изменен: <code>%s</code>",
        "Your ID has been changed: <code>%s</code>"),
    STATS(
        """
            👮‍♂️ <i>Barcha foydalanuvchilar:</i> <b>%s</b>
             - <i>So'nggi haftada:</i> <b>%s</b>
             - <i>So'nggi oyda:</i> <b>%s</b>
                    
            🔖 <i>Barcha yuborilgan xabarlar:</i> <b>%s</b>""",
        """
            👮‍♂️ <i>Все пользователи:</i> <b>%s</b>
             - <i>На прошлой неделе:</i> <b>%s</b>
             - <i>В прошлом месяце:</i> <b>%s</b>
                    
            🔖 <i>Все отправленные сообщения:</i> <b>%s</b>""",
        """
            👮‍♂️ <i>All users:</i> <b>%s</b>
             - <i>In the last week:</i> <b>%s</b>
             - <i>In the last month:</i> <b>%s</b>
                    
            🔖 <i>All sent messages:</i> <b>%s</b>"""),
    YOUR_LINK("""
        Savollar uchun havola:
        t.me/anonimqabot?start=%s
            
        Ushbu havolani do'stlaringiz va obunachilaringizga ko'rsating va ulardan anonim savollar va javoblarni oling!""",
        """
            Твоя ссылкa для вопросов:
            t.me/anonimqabot?start=%s
                            
            Покажи эту ссылку друзьям и подписчикам и получай от них анонимные вопросы и отвечай!""",
        """
            Your link for questions:
            t.me/anonimqabot?start=%s
                            
            Show this link to your friends and subscribers and receive anonymous questions and answers from them!"""),
    DEFAULT_MESSAGE("<b>Xabar yuborish uchun avval qabul qiluvchi ID sini kiriting</b> /set",
        "<b>Чтобы отправить сообщение, сначала введите идентификатор получателя</b> /set",
        "<b>To send a message, first enter the recipient ID</b> /set"),
    CANCELED("✅ Yuborish bekor qilindi", "✅ Отправка была отменена", "✅ Sending was cancelled"),
    MY_ID("🆔 <code>%s</code>\n\nAnonim xabarlarni qabul qilish uchun havolangiz:\nt.me/anonimqabot?start=%s",
        "🆔 <code>%s</code>\n\nВаша ссылка для получения анонимных сообщений:\nt.me/anonimqabot?start=%s",
        "🆔 <code>%s</code>\n\nYour link to receive anonymous messages::\nt.me/anonimqabot?start=%s");

    private final String uz;
    private final String ru;
    private final String en;

    public String get(Language lang) {
        switch (lang) {
            case EN -> {
                return this.en;
            }
            case RU -> {
                return this.ru;
            }
            default -> {
                return this.uz;
            }
        }
    }
}
