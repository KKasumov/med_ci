package com.kasumov.med_ci.model.entity.economic;


import com.kasumov.med_ci.model.enums.MonthEnum;

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
import java.math.BigDecimal;

/**
 * муниципальный заказ для смо на 1 месяц
 */

@Entity
@Table(name = "municipal_order_for_smo_on_month")
public class MunicipalOrderForSmoOnMonth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * месяц
     */
    @Column(name = "month", nullable = false)
    @Enumerated(EnumType.STRING)
    private MonthEnum month;

    /**
     * сумма денег на 1 месяц
     */
    @Column(name = "money", nullable = false)
    private BigDecimal money;

    /**
     * муниципальный заказ для смо на 1 год
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipal_order_for_smo_id", nullable = false)
    private MunicipalOrderForSmo orderForSmoOnYear;

}
