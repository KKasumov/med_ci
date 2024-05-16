package com.kasumov.med_ci.model.entity.medical;

import com.kasumov.med_ci.model.entity.economic.Order;
import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

/**
 * Appeal - Обращение
 * <p>
 * Пациент обращается с заболеванием. И лечат заболевание.
 * При первом посещении устанавливается заболевание, которое будут лечить.
 * Обращение может лечиться как в одно посещение, так и в несколько.
 * Врач, оказывающий услуги в рамках одного обращения, может меняться(заболел), но не должна меняться отделение врача.
 * Любой из врачей, участвовавших в лечении пациента, может закрыть/открыть обращение.
 * Пока обращение не закрыто, врач может его править (только те посещения, где лечил он).
 * Когда обращение закрыто, оно может попасть в счет и пока оно в счете, его нельзя править никому.(isReady = true)
 * Обращение может быть удалено из сформированного счета для модификации, оно теряет ссылку на счет.
 * Любой из врачей, участвовавших в лечении пациента может открыть обращение для устранения ошибок в его посещении, если оно не в счете.
 * Закрытое обращение нельзя модифицировать
 */

@Entity
@Getter
@Setter
@Table(name = "appeal")
public class Appeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Дата закрытия обращения.
     * Выставляется по дате последнего посещения
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    @Column(name = "closed_date")
    private LocalDate closedDate;

    /**
     * Флаг - готово ли обращение что бы попасть в счет.
     * Если true, то обращение может попасть в счет. Если false - нет
     */
    @Column(name = "is_ready", nullable = false)
    private boolean isReady;

    /**
     * тип страхования: омс, дмс
     */
    @Column(name = "insurance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    /**
     * Счет с которым может быть связано обращение.
     * Если обращение связано со счетом, то его нельзя модифицировать
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * пациент
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_history_id", nullable = false)
    private PatientHistory patientHistory;

    /**
     * заболевание
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id", nullable = false)
    private Disease disease;



    /**
     * посещения во время которых лечилось это заболевание
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appeal")
    private Set<Visit> visits;

}
