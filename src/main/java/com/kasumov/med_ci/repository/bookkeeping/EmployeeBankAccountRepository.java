package com.kasumov.med_ci.repository.bookkeeping;

import com.kasumov.med_ci.model.entity.bookkeeping.account.EmployeeBankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeBankAccountRepository extends JpaRepository<EmployeeBankAccount, Long> {
}
