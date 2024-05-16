package com.kasumov.med_ci.model.dto.user.doctor;

import com.kasumov.med_ci.model.dto.user.attestation.AttestationDto;
import com.kasumov.med_ci.model.enums.Gender;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.util.List;

@Builder
public record DoctorDto(long id,
                        String email,
                        String firstName,
                        String lastName,
                        @Nullable String patronymic,
                        String birthday,
                        Gender gender,
                        @Nullable List<AttestationDto> attestationDto)
                        {
}
