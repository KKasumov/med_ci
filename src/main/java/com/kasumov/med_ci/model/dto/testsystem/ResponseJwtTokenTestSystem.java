package com.kasumov.med_ci.model.dto.testsystem;

import lombok.Builder;

@Builder
public record ResponseJwtTokenTestSystem(String jwt, String expirationDate) {
}
