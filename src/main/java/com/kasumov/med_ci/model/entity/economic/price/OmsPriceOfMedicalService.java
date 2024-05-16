package com.kasumov.med_ci.model.entity.economic.price;


import com.kasumov.med_ci.model.entity.medical.MedicalService;
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
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * цена медицинской услуги
 * Услуга может стоить 0 УЕТ, что означает, что она бесплатная.
 * Каждая услуга стоит определенный УЕТ в фиксированный период времени, кратный дням,
 * например:
 * с 28.07.2021 по 25.09.2021 цена 0.25
 * с 26.09.2021 по 03.12.2021 цена 0.30
 * с 04.12.2021 по null       цена null (означает - с 4.12.21 услуга не оказывается)
 * с 01.01.2022 по null       цена 0.20 (означает - с 01.01.2022 по настоящее услуга оказывается)
 */

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "oms_price_of_medical_service")
public class OmsPriceOfMedicalService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата начала действия цены на услугу
     */
    @Column(name = "day_from", nullable = false)
    private LocalDate dayFrom;

    /**
     * дата окончания действия цены на услугу
     */
    @Column(name = "day_to")
    private LocalDate dayTo;

    /**
     * цена в ует - для омс, например 2.63
     */
    @Column(name = "yet", nullable = false)
    private BigDecimal yet;

    /**
     * медицинская услуга
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_service_id", nullable = false)
    private MedicalService medicalService;

}
