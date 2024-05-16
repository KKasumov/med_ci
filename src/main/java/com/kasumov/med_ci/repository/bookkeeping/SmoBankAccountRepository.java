package com.kasumov.med_ci.repository.bookkeeping;

import com.kasumov.med_ci.model.entity.bookkeeping.account.SmoBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmoBankAccountRepository extends JpaRepository<SmoBankAccount, Long> {
}
