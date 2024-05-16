package com.kasumov.med_ci.model.dto.user.polis;

import com.kasumov.med_ci.model.dto.economic.smo.SmoForPolisDto;
import com.kasumov.med_ci.model.enums.InsuranceType;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record PolisDto(
        long id,
        InsuranceType insuranceType,
        @Nullable String serial,
        String number,
        String dateStart,       //format dd.MM.yyyy
        String dateEnd,         //format dd.MM.yyyy
        boolean isDeprecated,
        SmoForPolisDto smo
) {
}
