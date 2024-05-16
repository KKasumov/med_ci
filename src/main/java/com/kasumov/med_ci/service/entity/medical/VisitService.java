package com.kasumov.med_ci.service.entity.medical;

import com.kasumov.med_ci.model.dto.medical.visit.VisitMedicalServiceNativeDto;

public interface VisitService {
    boolean existsByMedicalServicesId(long medicalServiceId);

    VisitMedicalServiceNativeDto findWithMedicalServicesForOms(long visitId);

    VisitMedicalServiceNativeDto findWithMedicalServicesForDms(long visitId);

}
