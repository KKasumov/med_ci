package com.kasumov.med_ci.model.dto.user.laborContract;

import lombok.Builder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;


@Builder
public record LaborContractForHiringDto(String startDate,
                                        @Nullable String endDate,
                                        BigDecimal part) {
}
