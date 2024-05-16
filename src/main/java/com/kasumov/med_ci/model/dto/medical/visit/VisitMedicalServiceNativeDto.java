package com.kasumov.med_ci.model.dto.medical.visit;

import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDtoNative;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface VisitMedicalServiceNativeDto {
    Long getVisitId();

    LocalDate getDayOfVisit();

    Long getDocId();

    String getDocFirstName();

    String getDocLastName();

    String getDocPatronymic();

    List<MedicalServiceDtoNative> getMedicalServiceDtoNative();

    BigDecimal getVisitTotalPrice();

}
