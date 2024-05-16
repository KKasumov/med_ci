package com.kasumov.med_ci.model.entity.bookkeeping;


import com.kasumov.med_ci.model.entity.bookkeeping.account.BankAccount;
import com.kasumov.med_ci.model.enums.PaymentStatus;

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
 * платежи проводимые в обе стороны
 */

@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * дата платежа
     */
    @Column(name = "date_pay", nullable = false)
    private LocalDate datePay;

    /**
     * сумма платежа
     */
    @Column(name = "money", nullable = false)
    private BigDecimal money;

    /**
     * счет отправителя с которого списаны средства
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_bank_account_id", nullable = false)
    private BankAccount payerBankAccount;

    /**
     * счет получателя на который переведены средства
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_bank_account_id", nullable = false)
    private BankAccount recipientBankAccount;

    /**
     * статус платежа
     */
    @Column(name = "payment_status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    /**
     * комментарий
     */
    @Column(name = "comment")
    private String comment;

}