package com.kasumov.med_ci.model.entity.user.items;


import com.kasumov.med_ci.model.enums.VocationType;
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
 * отпуск
 * они бывают трех видов: опуск по болезни, трудовой отпуск, отпуск за свой счет
 */

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vacation")
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата начала отпуска
     */
    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    /**
     * дата окончания отпуска
     */
    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    /**
     * тип отпуска
     */
    @Column(name = "vocation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private VocationType vocationType;

    /**
     * комментарий к отпуску
     */
    @Column(name = "comment")
    private String comment;

    /**
     * связь с трудовым договором сотрудника
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "labor_contract_id", nullable = false)
    private LaborContract laborContract;

}
