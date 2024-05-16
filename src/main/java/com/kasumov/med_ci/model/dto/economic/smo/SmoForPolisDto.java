package com.kasumov.med_ci.model.dto.economic.smo;

import com.kasumov.med_ci.model.enums.Region;
import lombok.Builder;

@Builder
public record SmoForPolisDto(long id,
                             String name,
                             Region region,
                             String code) {
}
