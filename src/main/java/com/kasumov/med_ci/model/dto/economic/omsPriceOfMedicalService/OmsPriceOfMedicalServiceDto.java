package com.kasumov.med_ci.model.dto.economic.omsPriceOfMedicalService;

import lombok.Builder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Builder
public record OmsPriceOfMedicalServiceDto(
        long id,
        String dayFrom ,      //format dd.MM.yyyy
        @Nullable
        String dayTo,       //format dd.MM.yyyy
        BigDecimal yet
) {
}
