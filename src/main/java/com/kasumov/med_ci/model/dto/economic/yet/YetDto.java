package com.kasumov.med_ci.model.dto.economic.yet;

import lombok.Builder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
@Builder
public record YetDto(
        long id,
        BigDecimal price,
        String dayFrom,
        @Nullable String dayTo) {
}
