package anonim.repo;

import anonim.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {
    Message findByTelegramMessageId(Integer telegramMessageId);

    boolean existsByTelegramMessageId(Integer telegramMessageId);
}
