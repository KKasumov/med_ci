package com.kasumov.med_ci.model.dto.user.attestation;

import lombok.Builder;

@Builder
public record NewAttestationDto(String dateFrom,
                               String dateTo,
                               String number,
                               long universityId) {
}
