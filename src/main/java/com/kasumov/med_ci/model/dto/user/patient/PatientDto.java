package com.kasumov.med_ci.model.dto.user.patient;

import com.kasumov.med_ci.model.enums.Gender;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record PatientDto(long id,
                         String email,
                         String snils,
                         String firstName,
                         String lastName,
                         @Nullable String patronymic,
                         String birthday,
                         Gender gender,
                         boolean isEnabled) {
}
