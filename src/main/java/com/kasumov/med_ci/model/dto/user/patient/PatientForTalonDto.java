package com.kasumov.med_ci.model.dto.user.patient;

import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record PatientForTalonDto(long id,
                                 String firstName,
                                 String lastName,
                                 @Nullable String patronymic,
                                 String birthday) {
}
