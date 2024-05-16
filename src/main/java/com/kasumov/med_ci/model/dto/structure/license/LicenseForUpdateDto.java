package com.kasumov.med_ci.model.dto.structure.license;

import lombok.Builder;

@Builder
public record LicenseForUpdateDto(long id,
                                  String name,
                                  String number,
                                  String startDate,
                                  String endDate) {
}
