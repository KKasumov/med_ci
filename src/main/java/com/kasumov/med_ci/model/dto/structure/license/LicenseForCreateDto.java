package com.kasumov.med_ci.model.dto.structure.license;

import lombok.Builder;

@Builder
public record LicenseForCreateDto(String name,
                                  String number,
                                  String startDate,
                                  String endDate,
                                  long medicalOrganizationId) {
}
