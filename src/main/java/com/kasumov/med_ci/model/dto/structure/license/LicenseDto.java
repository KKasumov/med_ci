package com.kasumov.med_ci.model.dto.structure.license;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude
@Builder
public record LicenseDto(long id,
                         String name,
                         String number,
                         String startDate,
                         String endDate) {
}
