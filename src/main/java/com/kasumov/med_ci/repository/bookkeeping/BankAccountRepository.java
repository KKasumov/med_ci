package com.kasumov.med_ci.repository.bookkeeping;

import com.kasumov.med_ci.model.entity.bookkeeping.account.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
