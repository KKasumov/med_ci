package com.kasumov.med_ci.model.dto.user.attestation;

import lombok.Builder;

@Builder
public record AttestationDto( String dateFrom,
                              String dateEnd,
                              String number) {
}
