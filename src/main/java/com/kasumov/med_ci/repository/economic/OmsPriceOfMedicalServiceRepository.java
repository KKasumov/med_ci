package com.kasumov.med_ci.repository.economic;

import com.kasumov.med_ci.model.entity.economic.price.OmsPriceOfMedicalService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OmsPriceOfMedicalServiceRepository extends JpaRepository<OmsPriceOfMedicalService, Long> {
}
