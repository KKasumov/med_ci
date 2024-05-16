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
 * приглашение для смены пароля учетной записи
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invite")
public class Invite {

    /**
     * для каждого мейла может быть только одно приглашение и оно перезатирается
     */
    @Id
    private String email;

    /**
     * токен отправляется приглашенному и не несет информации о нем
     */
    @Column(unique = true, nullable = false)
    private String token;

    /**
     * время жизни токена
     */
    @Column(nullable = false)
    private LocalDateTime expirationDate;
}
