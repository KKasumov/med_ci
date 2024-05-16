package com.kasumov.med_ci.repository.economic;

import com.kasumov.med_ci.model.entity.economic.MunicipalOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MunicipalOrderRepository extends JpaRepository<MunicipalOrder, Long> {
}
