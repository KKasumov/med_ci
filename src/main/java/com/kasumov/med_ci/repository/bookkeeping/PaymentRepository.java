package com.kasumov.med_ci.repository.bookkeeping;

import com.kasumov.med_ci.model.entity.bookkeeping.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
