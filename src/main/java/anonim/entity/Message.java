package anonim.entity;

import anonim.entity.auth.AuthUser;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "message")
@Builder
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "telegram_message_id")
    private Integer telegramMessageId;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Message question;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AuthUser owner;


}
