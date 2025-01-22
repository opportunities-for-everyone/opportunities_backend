package com.project.opportunities.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "telegram_chats")
public class TelegramChat {
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String chatId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id", nullable = false)
    private User user;
}
