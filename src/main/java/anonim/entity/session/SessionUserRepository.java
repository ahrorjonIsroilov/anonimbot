package anonim.entity.session;

import com.redis.om.spring.repository.RedisDocumentRepository;

public interface SessionUserRepository extends RedisDocumentRepository<SessionUser, Long> {
}
