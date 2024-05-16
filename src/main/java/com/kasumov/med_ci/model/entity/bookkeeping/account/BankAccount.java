package com.kasumov.med_ci.model.entity.bookkeeping.account;


import com.kasumov.med_ci.model.entity.bookkeeping.Bank;
import com.kasumov.med_ci.model.entity.bookkeeping.Payment;
import lombok.Getter;
import lombok.Setter;

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
import java.util.Set;

/**
 * счет в банке
 */
@Entity
@Setter
@Getter
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * название счета
     */
    @Column(name = "name")
    private String name;

    /**
     * номер счета
     */
    @Column(name = "number", nullable = false, unique = true)
    private String number;

    /**
     * флаг - проставляется бухгалтером если счет не надо использовать
     */
    @Column(name = "is_disabled", nullable = false)
    private boolean isDisabled;

    /**
     * банк
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    /**
     * счет отправителя с которого списаны средства
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "payerBankAccount")
    private Set<Payment> paymentsAccountIsPayer;

    /**
     * счет отправителя с которого списаны средства
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recipientBankAccount")
    private Set<Payment> paymentsAccountIsRecipient;

}