package com.kasumov.med_ci.model.dto.user.diploma;

import lombok.Builder;

@Builder
public record DiplomaForHiringDto(String serialNumber,
                                  String endDate,
                                  Long universityId) {
}
