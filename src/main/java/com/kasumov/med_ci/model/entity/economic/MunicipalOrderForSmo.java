package com.kasumov.med_ci.model.entity.economic;


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
import java.util.Set;

/**
 * муниципальный заказ для смо на 1 год
 */

@Entity
@Table(name = "municipal_order_for_smo")
public class MunicipalOrderForSmo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * сумма выделенная на год для лечения пациентов этой СМО
     */
    @Column(name = "money", nullable = false)
    private BigDecimal money;

    /**
     * муниципальный заказ на ЛПУ
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipal_order_id", nullable = false)
    private MunicipalOrder municipalOrder;

    /**
     * смо
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smo_id", nullable = false)
    private Smo smo;



    /**
     * муниципальный заказ для смо в разрезе месяцев
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderForSmoOnYear")
    private Set<MunicipalOrderForSmoOnMonth> ordersForSmoOnMonth;

}
