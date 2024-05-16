package com.kasumov.med_ci.model.entity.user.items;

import com.kasumov.med_ci.model.entity.user.history.UserHistory;
import com.kasumov.med_ci.model.enums.Gender;
import com.kasumov.med_ci.model.enums.IdentityDocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * документ подтверждающий личность.
 * у человека не может быть более одного действующего паспорта
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "identity_document")
public class IdentityDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * тип документа
     * при достижении 18 лет счета не могут быть оказаны в детских отдетениях
     */
    @Column(name = "document_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private IdentityDocumentType documentType;

    /**
     * серия
     */
    @Column(name = "serial", nullable = false)
    private String serial;

    /**
     * номер
     */
    @Column(name = "number", nullable = false)
    private String number;

    /**
     * дата начала действия
     */
    @Column(name = "date_start", nullable = false)
    private LocalDate dateStart;

    /**
     * имя
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * фамилия
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * отчество.
     */
    @Column(name = "patronymic")
    private String patronymic;

    /**
     * день рождения
     */
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday;

    /**
     * пол
     */
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    /**
     * если документ потерял актуальность, это поле выставляется в true
     */
    @Column(name = "is_deprecated", nullable = false)
    private boolean isDeprecated;

    /**
     * связь с юсером
     **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_history_id", nullable = false)
    private UserHistory userHistory;

}
