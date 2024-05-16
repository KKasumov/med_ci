package com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService;

import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDto;
import lombok.Builder;
import java.math.BigDecimal;


@Builder
public record PayPriceOfMedicalServiceDto(
        Long id,
        String dayFrom,
        String dayTo,
        BigDecimal moneyInPrice,
        MedicalServiceDto medicalService
) {
}