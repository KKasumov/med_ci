package com.kasumov.med_ci.model.dto.medical.medicalService;

import lombok.Builder;

@Builder
public record MedicalServiceDtoNativeRec(
        // Long medServiceId,
        long id,
        // String medServiceIdentifier,
        String identifier,
        //String medServiceName
        String name

) {
}
