package com.kasumov.med_ci.repository.bookkeeping;

import com.kasumov.med_ci.model.entity.bookkeeping.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Bank, Long> {
}
