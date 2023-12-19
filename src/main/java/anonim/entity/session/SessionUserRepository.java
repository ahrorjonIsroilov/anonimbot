package anonim.entity.session;

import com.redis.om.spring.repository.RedisDocumentRepository;
import org.springframework.stereotype.Repository;

public interface SessionUserRepository extends RedisDocumentRepository<SessionUser, Long> {
    Boolean existsByChatIdAndJoined(Long chatId, Boolean joined);

    Boolean existsByJoined(Boolean joined);
}
