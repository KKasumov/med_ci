package com.kasumov.med_ci.model.dto.medical.talon;

import com.kasumov.med_ci.model.dto.user.doctor.DoctorForTalonDto;
import com.kasumov.med_ci.model.dto.user.patient.PatientForTalonDto;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record TalonDto(Long id,
                       String time,
                       DoctorForTalonDto doctor,
                       @Nullable
                       PatientForTalonDto patient) {
}
