package anonim.entity;

import anonim.entity.auth.AuthUser;
import anonim.enums.MessageType;
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

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column(name = "content_path")
    private String contentPath;

    @Column(name = "file_id")
    private String fileId;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private AuthUser owner;


}
