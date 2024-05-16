package com.kasumov.med_ci.model.dto.medical.visit;

import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDtoNativeRec;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForVisitNativeDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record VisitMedicalServiceNativeDtoRec(
        long id,
        String dayOfVisit,
        DoctorForVisitNativeDto doctor,
        List<MedicalServiceDtoNativeRec> medicalServices,
        BigDecimal money
) {
}
