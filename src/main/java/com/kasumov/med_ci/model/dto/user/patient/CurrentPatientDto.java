package com.kasumov.med_ci.model.dto.user.patient;

import com.kasumov.med_ci.model.enums.Gender;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record CurrentPatientDto(long id,
                                String email,
                                String firstName,
                                String lastName,
                                @Nullable String patronymic,
                                String birthday,
                                Gender gender) {
}
