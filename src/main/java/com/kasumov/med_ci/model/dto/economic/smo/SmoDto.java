package com.kasumov.med_ci.model.dto.economic.smo;

import com.kasumov.med_ci.model.enums.Region;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record SmoDto(long id,
                     String name,
                     Region region,
                     String startDate,
                     @Nullable String endDate,
                     String code) {
}
