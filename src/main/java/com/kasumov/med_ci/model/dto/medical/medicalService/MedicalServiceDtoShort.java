package com.kasumov.med_ci.model.dto.medical.medicalService;

import lombok.Builder;

@Builder
public record MedicalServiceDtoShort(long id,
                                     String identifier,
                                     String name) {
}

