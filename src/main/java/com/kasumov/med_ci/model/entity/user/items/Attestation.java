package com.kasumov.med_ci.model.entity.user.items;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * аттестация.
 * Ежегодное подтверждение квалификации медицинского специалиста.
 * Сотруднику запрещено работать без этого документа.
 * Выдается на 1 год
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attestation")
public class Attestation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата начала действия аттестации
     */
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    /**
     * дата окончания действия аттестации
     */
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    /**
     * номер документа
     */
    @Column(name = "number", unique = true, nullable = false)
    private String number;

    /**
     * образовательное учреждение
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    /**
     * связь с трудовым договором
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "labor_contract_id", nullable = false)
    private LaborContract laborContract;

}
