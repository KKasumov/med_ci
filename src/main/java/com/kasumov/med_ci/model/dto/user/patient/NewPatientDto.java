package com.kasumov.med_ci.model.dto.user.patient;

import com.kasumov.med_ci.model.dto.user.identityDocument.NewIdentityDocumentDto;
import com.kasumov.med_ci.model.dto.user.polis.NewPolisDto;
import lombok.Builder;

@Builder
public record NewPatientDto(String email,
                            String snils,
                            NewPolisDto polis,
                            NewIdentityDocumentDto identityDocument) {
}