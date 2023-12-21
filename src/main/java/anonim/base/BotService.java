package anonim.base;

import anonim.entity.Message;
import anonim.entity.auth.AuthUser;
import anonim.entity.session.SessionUserRepository;
import anonim.enums.Role;
import anonim.repo.AuthRepo;
import anonim.repo.MessageRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public record BotService(AuthRepo repo, MessageRepo messageRepo, SessionUserRepository sessionUserRepository) {
    public Boolean existUser(Long chatId) {
        return repo.existsByChatId(chatId);
    }

    public AuthUser getUser(Long chatId) {
        return repo.getByChatId(chatId);
    }

    public AuthUser getUser(UUID userId) {
        return repo.findById(userId).orElse(null);
    }

    public AuthUser getUser(String identifier) {
        return repo.findByIdentifier(identifier);
    }

    public void saveUser(AuthUser user) {
        repo.save(user);
    }

    public Message saveMessage(Message message) {
        return messageRepo.save(message);
    }

    public Message getMessage(Long id) {
        return messageRepo.findById(id).orElse(null);
    }

    public Message getMessage(Integer tgMessageId) {
        return messageRepo.findByTelegramMessageId(tgMessageId);
    }

    public Boolean existsByMessageId(Integer messageId) {
        return messageRepo.existsByTelegramMessageId(messageId);
    }

    public List<AuthUser> getAdmins() {
        List<AuthUser> admins = repo.findAllByRole(Role.ADMIN);
        return admins;
    }

    public List<AuthUser> getJoinedUsers() {
        return repo.findAllByRoleAndJoined(Role.USER, true);
    }

    public void deleteUser(Long chatId) {
        repo.deleteByChatId(chatId);
    }

    public boolean existUser(String phoneNumber) {
        return repo.existsByPhone(phoneNumber);
    }

    public boolean existByIdentifier(String identifier) {
        return repo.existsByIdentifier(identifier);
    }

    public boolean existUserAndJoined(String phoneNumber) {
        return repo.existsByPhoneAndJoined(phoneNumber, true);
    }

    public Integer getUsersCount() {
        return (int) repo.count();
    }

    public Long getMessagesCount() {
        return messageRepo.count();
    }

    public Integer getUsersCount(LocalDateTime dateTime) {
        return (int) repo.countByCreatedAtGreaterThanEqual(dateTime);
    }
}
