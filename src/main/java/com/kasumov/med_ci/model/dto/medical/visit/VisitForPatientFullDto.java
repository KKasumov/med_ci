package com.kasumov.med_ci.model.dto.medical.visit;

import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDtoShort;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForTalonDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record VisitForPatientFullDto(
        long id,
        String dayOfVisit,      //dd.MM.yyyy HH:mm
        DoctorForTalonDto doctor,
        List<MedicalServiceDtoShort> medicalServices,
        BigDecimal money
) {
}
