package com.kasumov.med_ci.model.entity.bookkeeping.account;


import com.kasumov.med_ci.model.entity.economic.Smo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("smobankaccount")
public class SmoBankAccount extends BankAccount {

    /**
     * счета смо
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smo_id")
    private Smo smo;

}
