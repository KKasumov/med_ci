package com.kasumov.med_ci.model.dto.testsystem;

import lombok.Builder;

@Builder
public record RequestPatientDtoTestSystem(String snils,
                                          String serialAndNumberDocument,
                                          String serialAndNumberPolis
) {
}