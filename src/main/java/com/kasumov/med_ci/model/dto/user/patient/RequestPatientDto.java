package com.kasumov.med_ci.model.dto.user.patient;

import lombok.Builder;

@Builder
public record RequestPatientDto(String snils,
                                String serialAndNumberDocument,
                                String serialAndNumberPolis) {
}
