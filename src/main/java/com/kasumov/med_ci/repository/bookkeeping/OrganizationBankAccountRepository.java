package com.kasumov.med_ci.repository.bookkeeping;

import com.kasumov.med_ci.model.entity.bookkeeping.account.OrganizationBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationBankAccountRepository extends JpaRepository<OrganizationBankAccount, Long> {
}
