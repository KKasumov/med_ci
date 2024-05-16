package com.kasumov.med_ci.model.entity.bookkeeping.account;


import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class OrganizationBankAccount extends BankAccount {

    /**
     * счета медининской организации
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_organization_id")
    private MedicalOrganization medicalOrganization;
}
