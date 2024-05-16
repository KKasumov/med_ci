package com.kasumov.med_ci.repository.economic;

import com.kasumov.med_ci.model.entity.economic.price.PayPriceOfMedicalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayPriceOfMedicalServiceRepository extends JpaRepository<PayPriceOfMedicalService, Long> {
}
