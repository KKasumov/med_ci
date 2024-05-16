package com.kasumov.med_ci.model.dto.user.doctor;

import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record DoctorForVisitNativeDto(
        long id,
        String firstName,
        String lastName,
        @Nullable String patronymic
) {
}
