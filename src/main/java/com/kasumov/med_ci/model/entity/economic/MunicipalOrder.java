package com.kasumov.med_ci.model.entity.economic;


import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Муниципальный заказ по ОМС
 */

@Entity
@Table(name = "municipal_order")
public class MunicipalOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * сумма выделенная на год
     */
    @Column(name = "money", nullable = false)
    private BigDecimal money;

    /**
     * последняя дата года - дата закрытия муниципального заказа
     */
    @Column(name = "period", nullable = false)
    private LocalDate period;

    /**
     * медицинская организация для которой выделен муниципальный заказ
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id", nullable = false)
    private MedicalOrganization medicalOrganization;



    /**
     * муниципальные заказы в разрезе СМО
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "municipalOrder")
    private Set<MunicipalOrderForSmo> ordersForSMO;

}
