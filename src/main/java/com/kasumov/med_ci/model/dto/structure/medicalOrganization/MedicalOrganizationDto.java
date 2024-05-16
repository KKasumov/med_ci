package com.kasumov.med_ci.model.dto.structure.medicalOrganization;

import com.kasumov.med_ci.model.dto.user.user.UserDto;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Builder
public record MedicalOrganizationDto(long id,
                                     String code,
                                     String name,
                                     @Nullable String legalAddress,
                                     String ogrn,
                                     String startDate,
                                     @Nullable String endDate,
                                     BigDecimal fullEmploymentStatusRange,
                                     @Nullable UserDto director,
                                     @Nullable UserDto ioDirector
) {

}
