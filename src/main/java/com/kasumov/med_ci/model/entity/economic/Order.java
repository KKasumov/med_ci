package com.kasumov.med_ci.model.entity.economic;


import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.enums.InsuranceType;
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
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Счет
 * В последний день каждого месяца экономист формирует счет на оплату в страховые организации по ОМС и ДМС.
 * В этот счет попадают все закрытые обращения в рамках ОМС или ДМС по СМО в указанный период.
 * Обращение может находиться только в одном счете.
 * Обращения, которые находятся в счете нельзя модифицировать
 * счет можно удалять и пересоздавать - Обращения не должны удаляться.
 * из счета можно удалять обращения - они не должны удаляться
 * можно проводить поиск новых закрытых обращений, которых нет в счете для добавления в счет
 */

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * тип страхования: омс, дмс
     */
    @Column(name = "insurance_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InsuranceType insuranceType;

    /**
     * комментарий
     * например: "АльфаСтрахование Основной за ноябрь по ОМС"
     */
    @Column(name = "comment")
    private String comment;

    /**
     * дата формирования счет-фактуры счета
     * чаще всего используется последний день месяца.
     * Все обращения в текущем счете должны быть закрыты в этом отчетном месяце, но не позже этой даты.
     */
    @Column(name = "date", nullable = false)
    private LocalDate date;

    /**
     * сумма денег за все услуги, входящие в этот счет
     */
    @Column(name = "money")
    private BigDecimal money;

    /**
     * флаг - сформирован ли счет.
     * если счет сформирован, это значит - он должен быть посчитан.
     * Значит - любая модификация счета запрещена. Но счет еще можно раcформировать.
     */
    @Column(name = "is_formed", nullable = false)
    private boolean isFormed;

    /**
     * флаг - счет принят к оплате.
     * если true - значит теперь совсем ничего нельзя менять. Значит выставлен к оплате
     * Далее СМО будет переводить деньги, надо контролировать что суммы совпадают.
     */
    @Column(name = "is_accepted_for_payment", nullable = false)
    private boolean isAcceptedForPayment;

    /**
     * флаг - счет оплачен
     */
    @Column(name = "is_payed", nullable = false)
    private boolean isPayed;

    /**
     * медицинская организация которая оказала услуги в данном счете
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id", nullable = false)
    private MedicalOrganization medicalOrganization;

    /**
     * принадлежность страховой медицинской организации
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smo_id", nullable = false)
    private Smo smo;

}
