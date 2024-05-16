package com.kasumov.med_ci.model.dto.structure.wage;

import lombok.Builder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Builder
public record NewWageDto(String dateStart,           //format dd.MM.yyyy
                         @Nullable String dateEnd,   //format dd.MM.yyyy
                         BigDecimal value) {
}
