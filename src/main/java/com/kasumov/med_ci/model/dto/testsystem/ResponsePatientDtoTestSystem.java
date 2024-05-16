package com.kasumov.med_ci.model.dto.testsystem;

import com.kasumov.med_ci.model.dto.user.identityDocument.IdentityDocumentForTestSystemResponseDto;
import com.kasumov.med_ci.model.dto.user.polis.PolisForTestSystemResponseDto;
import com.kasumov.med_ci.model.enums.PatientStatus;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record ResponsePatientDtoTestSystem(
        PatientStatus patientStatus,
        @Nullable String snils,
        @Nullable IdentityDocumentForTestSystemResponseDto document,
        @Nullable PolisForTestSystemResponseDto polis) {
}
