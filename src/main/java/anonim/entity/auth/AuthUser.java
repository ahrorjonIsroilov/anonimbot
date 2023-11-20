package anonim.entity.auth;

import anonim.entity.Auditable;
import anonim.enums.Language;
import anonim.enums.Role;
import anonim.enums.State;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "users")
public class AuthUser extends Auditable {
    @Column(name = "chat_id", unique = true)
    Long chatId;

    @Column(name = "username")
    String username;

    @Column(name = "identifier")
    String identifier;
    @Column(name = "target_id")
    String targetId;

    @Column(name = "phone")
    String phone;

    @Column(name = "joined")
    boolean joined;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    Role role;

    @Column(name = "blocked", nullable = false)
    boolean blocked;

    @Enumerated(EnumType.STRING)
    @Column(name = "lang")
    Language language;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    State state;
}
