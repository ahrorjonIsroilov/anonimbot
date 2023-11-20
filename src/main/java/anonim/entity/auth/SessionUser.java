package anonim.entity.auth;

import anonim.enums.Language;
import anonim.enums.Role;
import anonim.enums.State;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class SessionUser {
    private Long chatId;
    private Role role;
    private State state;
    private Boolean joined;
    private Boolean blocked;

    private String identifier;
    private String targetId;

    private Language language;
    private Map<SessionElement, Object> elements;

    @Builder
    public SessionUser(Long chatId, Role role, State state, Boolean joined, Boolean blocked, String identifier, String targetId, Language language) {
        this.chatId = chatId;
        this.role = role;
        this.blocked = blocked;
        this.state = state;
        this.joined = joined;
        this.identifier = identifier;
        this.targetId = targetId;
        this.language = language;
        this.elements = new HashMap<>();
    }
}
