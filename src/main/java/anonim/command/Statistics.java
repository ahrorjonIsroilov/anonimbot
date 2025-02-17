package anonim.command;


import anonim.base.BotService;
import anonim.base.command.TextCommand;
import anonim.config.BotConfig;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Formatting;
import anonim.enums.Role;
import anonim.util.Words;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class Statistics extends TextCommand {
    public Statistics(BotService service, SessionUserRepository repository) {
        super(service, repository);
    }

    @Override
    protected void handle(Update update) {


        NumberFormat format = NumberFormat.getInstance();
        if (Objects.requireNonNull(session.getRole(chatId)) == Role.ADMIN) {
            LocalDateTime now = LocalDateTime.now();
            Integer usersCount = service.getUsersCount();
            Integer usersCountInLastWeek = service.getUsersCount(
                now.minusDays(now.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue())
                    .withHour(0).withMinute(0).withSecond(0).withNano(0));
            Integer usersCountInLastMonth = service.getUsersCount(
                now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0)
            );
            Long messagesCount = service.getMessagesCount();

            sendMessage(Words.STATS.get(lang).formatted(
                format.format(usersCount),
                format.format(usersCountInLastWeek),
                format.format(usersCountInLastMonth),
                format.format(messagesCount)
            ), Formatting.CUSTOM);
        }
    }

    @Override
    public String getName() {
        return BotConfig.COMMAND_INIT + "stat";
    }
}
