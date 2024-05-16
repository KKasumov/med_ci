package com.kasumov.med_ci.model.entity.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * код для регистрации в телеграм боте
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "telegram_code")
public class TelegramCode {
    /**
     * для каждого мейла может быть только один код и он перезатирается
     */
    @Id
    private String email;

    /**
     * Шестизначный код для регистрации в телеграм боте
     */
    @Column(unique = true, nullable = false)
    private String code;

    /**
     * chatId телеграм бота
     */
    @Column(unique = true, nullable = false)
    private long chatId;

    /**
     * время жизни кода
     */
    @Column(nullable = false)
    private LocalDateTime expirationDate;
}
