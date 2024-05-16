package com.kasumov.med_ci.model.dto.medical.visit;

import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDto;
import com.kasumov.med_ci.model.dto.user.doctor.DoctorForTalonDto;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record VisitDto(
        long id,
        String dayOfVisit,      //dd.MM.yyyy HH:mm
        DoctorForTalonDto doctor,
        List<MedicalServiceDto> medicalServices,
        BigDecimal money) {

}
