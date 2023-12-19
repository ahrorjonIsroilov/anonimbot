package anonim.entity.session;

import anonim.enums.Language;
import anonim.enums.Role;
import anonim.enums.State;
import com.redis.om.spring.annotations.Document;
import com.redis.om.spring.annotations.Indexed;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.redis.core.RedisHash;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@Document
@RedisHash
public class SessionUser {
    @Id
    @Indexed
    private Long chatId;
    private Role role;
    private State state;
    private Boolean joined;
    private Boolean blocked;

    private String identifier;
    private String targetId;

    private Language language;
    private Map<SessionElement, Object> elements;

    @PersistenceCreator
    public SessionUser(Long chatId, Role role, State state, Boolean joined, Boolean blocked, String identifier, String targetId, Language language, Map<SessionElement, Object> elements) {
        this.chatId = chatId;
        this.role = role;
        this.state = state;
        this.joined = joined;
        this.blocked = blocked;
        this.identifier = identifier;
        this.targetId = targetId;
        this.language = language;
        this.elements = elements;
    }
}
