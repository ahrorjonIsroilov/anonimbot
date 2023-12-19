package anonim.entity.session;

import anonim.entity.auth.AuthUser;
import anonim.enums.Role;
import anonim.enums.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Session {
    private final SessionUserRepository repository;

    public static Session build(SessionUserRepository repository) {
        return new Session(repository);
    }

    public Boolean exist(Long chatId) {
        return
            repository.existsById(chatId);
    }

    public Boolean existAndJoined(Long chatId) {
        return repository.existsByChatIdAndJoined(chatId, true);
    }

    public Boolean joined(Long chatId) {
        Optional<SessionUser> sessionOptional = repository.findById(chatId);
        return sessionOptional.isPresent() && sessionOptional.get().getJoined();
    }

    public Role getRole(Long chatId) {
        SessionUser sessionUser = get(chatId);
        return sessionUser.getRole();
    }

    public Boolean checkState(State state, Long chatId) {
        SessionUser sessionUser = get(chatId);
        return sessionUser.getState().equals(state);
    }

    public void set(SessionUser sessionUser) {
        repository.save(sessionUser);
    }

    public SessionUser prepare(AuthUser user) {
        SessionUser sessionUser = new SessionUser(
            user.getChatId(),
            user.getRole(),
            user.getState(),
            user.isJoined(),
            user.isBlocked(),
            user.getIdentifier(),
            user.getTargetId(),
            user.getLanguage(),
            new HashMap<>()
        );
        return repository.save(sessionUser);
    }

    public void setState(State state, Long chatId) {
        SessionUser sessionUser = get(chatId);
        sessionUser.setState(state);
        repository.save(sessionUser);
    }

    public void setBlocked(Long chatId) {
        SessionUser sessionUser = get(chatId);
        sessionUser.setBlocked(!sessionUser.getBlocked());
        repository.save(sessionUser);
    }

    public void setElement(SessionElement elementType, Object data, Long chatId) {
        SessionUser sessionUser = get(chatId);
        if (sessionUser.getElements() == null)
            sessionUser.setElements(new HashMap<>());
        sessionUser.getElements().put(elementType, data);
        repository.save(sessionUser);
    }

    public <T> T getElement(Long chatId, SessionElement elementType) {
        SessionUser sessionUser = get(chatId);
        Object object = sessionUser.getElements().get(elementType);
        return (T) object;
    }

    public State getState(Long chatId) {
        SessionUser sessionUser = get(chatId);
        return sessionUser.getState();
    }

    public void remove(Long chatId) {
        repository.deleteById(chatId);
    }

    public Boolean matchAnyRole(Long chatId, Role... role) {
        Role[] clone = role.clone();
        SessionUser sessionUser = get(chatId);
        return Arrays.stream(clone).anyMatch(userRole -> sessionUser.getRole().equals(userRole));
    }

    public SessionUser get(Long chatId) {
        return repository.findById(chatId).orElseThrow(() -> new RuntimeException("Session is null"));
    }

    public void clearElement(SessionElement element, Long chatId) {
        SessionUser s = get(chatId);
        for (Map.Entry<SessionElement, Object> objectEntry : s.getElements().entrySet())
            if (objectEntry.getKey().equals(element)) objectEntry.setValue(null);
    }
}
