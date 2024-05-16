package com.kasumov.med_ci.model.dto.structure.wage;

import lombok.Builder;

import javax.annotation.Nullable;
import java.math.BigDecimal;
@Builder
public record WageDto(long id,
                      String dateStart,           //format dd.MM.yyyy
                      @Nullable String dateEnd,   //format dd.MM.yyyy
                      BigDecimal value) {
}
