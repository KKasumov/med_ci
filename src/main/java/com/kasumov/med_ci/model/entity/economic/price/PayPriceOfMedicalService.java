package com.kasumov.med_ci.model.entity.economic.price;


import com.kasumov.med_ci.model.entity.medical.MedicalService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
 * цена медицинской услуги для дмс в рублях
 */

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pay_price_of_medical_service")
public class PayPriceOfMedicalService {

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
     * цена в рублях - для дмс и платных услуг, например 2500.50
     */
    @Column(name = "money", nullable = false)
    private BigDecimal moneyInPrice;

    /**
     * медицинская услуга
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_service_id", nullable = false)
    private MedicalService medicalService;


}
