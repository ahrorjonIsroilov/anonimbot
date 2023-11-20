package anonim.repo;

import anonim.base.BaseRepo;
import anonim.entity.auth.AuthUser;
import anonim.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AuthRepo
    extends JpaRepository<AuthUser, UUID>, JpaSpecificationExecutor<AuthUser>, BaseRepo {
    long countByCreatedAtGreaterThanEqual(LocalDateTime createdAt);

    @Transactional
    @Modifying
    void deleteByChatId(Long chatId);

    AuthUser findByIdentifier(String identifier);

    AuthUser findByRole(Role role);

    boolean existsByPhone(String phone);

    boolean existsByIdentifier(String identifier);

    boolean existsByPhoneLikeIgnoreCase(String phone);

    Boolean existsByChatId(Long chatId);

    AuthUser getByChatId(Long chatId);

    List<AuthUser> findAllByRole(Role role);

    boolean existsByPhoneAndJoined(String phone, Boolean joined);

    List<AuthUser> findAllByRoleAndJoined(Role role, Boolean joined);
}
