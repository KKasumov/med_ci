package com.kasumov.med_ci.model.dto.user.doctor;

import com.kasumov.med_ci.model.enums.Gender;
import lombok.Builder;

@Builder
public record DoctorForPatientDto(long id,
                                  String firstName,
                                  String lastName,
                                  String patronymic,
                                  Gender gender) {
}
