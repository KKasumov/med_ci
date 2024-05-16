package com.kasumov.med_ci.model.dto.user.patient;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentForResponseDto;
import com.kasumov.med_ci.model.dto.user.polis.PolisForResponseDto;
import com.kasumov.med_ci.model.enums.PatientStatus;
import lombok.Builder;

@Builder
public record ResponsePatientDto(PatientStatus patientStatus,
                                String snils,
                                IdentityDocumentForResponseDto document,
                                PolisForResponseDto polis) {
}
