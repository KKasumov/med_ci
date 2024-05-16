package com.kasumov.med_ci.model.dto.medical.appeal;

import com.kasumov.med_ci.model.dto.medical.disease.DiseaseDto;
import com.kasumov.med_ci.model.enums.InsuranceType;
import com.kasumov.med_ci.model.dto.medical.visit.VisitMedicalServiceNativeDtoRec;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record AppealForPatientNativeFullDto(
        long id,
        @Nullable
        String closedDate, //dd.MM.yyyy HH:mm
        InsuranceType insuranceType,
        DiseaseDto disease,
        List<VisitMedicalServiceNativeDtoRec> visits,
        BigDecimal money
) {
}
