package com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record NewPayPriceOfMedicalServiceDto(
        String dayFrom,
        String dayTo,
        BigDecimal moneyInPrice
) {
}
