package anonim.entity.auth;

import anonim.enums.Role;
import anonim.enums.State;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class Session {
    private static final Session finalSession = new Session();
    private final Map<Long, SessionUser> session;

    public Session() {
        this.session = new HashMap<>();
    }

    public static Session build() {
        return finalSession;
    }

    public Boolean exist(Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        return Objects.nonNull(sessionUser);
    }

    public Boolean existAndJoined(Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        return Objects.nonNull(sessionUser) && sessionUser.getJoined();
    }

    public Boolean joined(Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        return Objects.nonNull(sessionUser) && sessionUser.getJoined();
    }

    public Role getRole(Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        return sessionUser.getRole();
    }

    public Boolean checkState(State state, Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        return sessionUser.getState().equals(state);
    }

    public void set(Long chatId, SessionUser sessionUser) {
        session.put(chatId, sessionUser);
    }

    public SessionUser prepare(AuthUser user) {
        return SessionUser.builder()
            .state(State.DEFAULT)
            .chatId(user.getChatId())
            .role(user.getRole())
            .language(user.getLanguage())
            .identifier(user.getIdentifier())
            .targetId(user.getTargetId())
            .joined(user.isJoined())
            .build();
    }

    public void setState(State state, Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        sessionUser.setState(state);
        session.put(chatId, sessionUser);
    }

    public void setBlocked(Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        sessionUser.setBlocked(!session.get(chatId).getBlocked());
        session.put(chatId, sessionUser);
    }

    public void setElement(SessionElement elementType, Object data, Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        sessionUser.getElements().put(elementType, data);
        set(chatId, sessionUser);
    }

    public <T> T getElement(Long chatId, SessionElement elementType) {
        SessionUser sessionUser = get(chatId);
        Object object = sessionUser.getElements().get(elementType);
        return (T) object;
    }

    public State getState(Long chatId) {
        SessionUser sessionUser = session.get(chatId);
        return sessionUser.getState();
    }

    public void remove(Long chatId) {
        session.remove(chatId);
    }

    public Boolean matchAnyRole(Long chatId, Role... role) {
        Role[] clone = role.clone();
        SessionUser sessionUser = session.get(chatId);
        return Arrays.stream(clone).anyMatch(userRole -> sessionUser.getRole().equals(userRole));
    }

    public SessionUser get(Long chatId) {
        return session.get(chatId);
    }

    public void clearElementForAll(SessionElement element) {
        for (Map.Entry<Long, SessionUser> entry : session.entrySet()) {
            SessionUser s = entry.getValue();
            for (Map.Entry<SessionElement, Object> objectEntry : s.getElements().entrySet())
                if (objectEntry.getKey().equals(element)) objectEntry.setValue(null);
        }
    }

    public void clearElement(SessionElement element, Long chatId) {
        SessionUser s = get(chatId);
        for (Map.Entry<SessionElement, Object> objectEntry : s.getElements().entrySet())
            if (objectEntry.getKey().equals(element)) objectEntry.setValue(null);
    }
}
