package com.kasumov.med_ci.model.entity.user.items;

import com.kasumov.med_ci.model.entity.economic.Smo;
import com.kasumov.med_ci.model.entity.user.history.PatientHistory;
import com.kasumov.med_ci.model.enums.InsuranceType;
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
 * полис
 */

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "polis")
public class Polis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * тип полиса
     */
    @Column(name = "insurance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    /**
     * серия
     */
    @Column(name = "serial")
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
     * дата окончания действия
     */
    @Column(name = "date_end", nullable = false)
    private LocalDate dateEnd;

    /**
     * если документ потерял актуальность, это поле выставляется в true
     */
    @Column(name = "is_deprecated", nullable = false)
    private boolean isDeprecated;

    /**
     * страховая медицинская организации которой принадлежит полис.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smo_id", nullable = false)
    private Smo smo;

    /**
     * связь с пациентом
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_history_id", nullable = false)
    private PatientHistory patientHistory;

}
