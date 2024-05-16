package com.kasumov.med_ci.model.dto.medical.medicalService;

import lombok.Builder;

@Builder
public record NewMedicalServiceDto(
        String identifier,
        String name,
        boolean isDisabled
       ) {
}
