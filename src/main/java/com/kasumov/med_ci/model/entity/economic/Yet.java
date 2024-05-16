package com.kasumov.med_ci.model.entity.economic;


import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
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
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * УЕТ - Условная единица трудозатрат - относительная сложность выполнения какой-либо работы.
 * Введена для удобства изменения оплаты труда сразу по всем услугам
 * Редактируется экономистом.
 * Цена может изменяться со временем.
 * Цена не может отсутствовать в какой-либо период времени.
 * Цена не может равняться 0р.
 * УЕТ может стоить определенную сумму денег в фиксированный период времени кратный месяцам,
 * например:
 * с 01.01.2021 по 31.05.2021 цена 78.50р
 * с 01.06.2021 по null       цена 78.00р (означает - по настоящее время)
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "yet")
public class Yet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * цена в рублях
     */
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /**
     * дата начала действия цены
     */
    @Column(name = "day_from", nullable = false)
    private LocalDate dayFrom;

    /**
     * дата окончания действия цены
     */
    @Column(name = "day_to")
    private LocalDate dayTo;

    /**
     * медицинская организация для которой высчитана УЕТ
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id", nullable = false)
    private MedicalOrganization medicalOrganization;

}
 