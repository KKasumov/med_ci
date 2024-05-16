package com.kasumov.med_ci.model.entity.economic;


import com.kasumov.med_ci.model.entity.bookkeeping.account.SmoBankAccount;
import com.kasumov.med_ci.model.entity.user.items.Polis;
import com.kasumov.med_ci.model.enums.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

/**
 * Страховая медицинская организация.
 */

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "smo")
public class Smo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название организации
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Код организации
     */
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    /**
     * Домашний регион в котором работает СМО.
     * PS: так и называется, например "СОГАЗ-МЕД Краснодар"
     */
    @Column(name = "region", nullable = false)
    @Enumerated(EnumType.STRING)
    private Region region;

    /**
     * дата начала деятельности юридического лица
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * дата прекращения деятельности юридического лица
     */
    @Column(name = "end_date")
    private LocalDate endDate;


    /**
     * счета которые должна оплатить страховая органицация за пролеченных пациентов
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "smo")
    private Set<Order> orders;

    /**
     * муниципальные заказы выделенные на смо
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "smo")
    private Set<MunicipalOrderForSmo> municipalOrdersForSmo;

    /**
     * полисы
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "smo")
    private Set<Polis> polises;

    /**
     * счета в банках
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "smo",
            cascade = CascadeType.REMOVE)
    private Set<SmoBankAccount> smoBankAccounts;
}
