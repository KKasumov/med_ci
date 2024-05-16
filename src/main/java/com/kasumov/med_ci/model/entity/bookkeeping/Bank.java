package com.kasumov.med_ci.model.entity.bookkeeping;


import com.kasumov.med_ci.model.entity.bookkeeping.account.BankAccount;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;

/**
 * банк
 */

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название банка
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * юридический адрес
     */
    @Column(name = "legal_address", nullable = false)
    private String legalAddress;

    /**
     * БИК - Банковский идентификационный код
     */
    @Column(name = "bik", nullable = false)
    private String bik;

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


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "bank")
    private Set<BankAccount> bankAccounts;
}