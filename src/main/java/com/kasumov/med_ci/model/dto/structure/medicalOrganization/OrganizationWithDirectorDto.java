package com.kasumov.med_ci.model.dto.structure.medicalOrganization;

import com.kasumov.med_ci.model.dto.user.user.UserDto;
import lombok.Builder;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
@Builder
public record OrganizationWithDirectorDto(long id,
                                          String code,
                                          String name,
                                          String legalAddress,
                                          String ogrn,
                                          String startDate,          //format dd.MM.yyyy
                                          @Nullable String endDate,   //format dd.MM.yyyy
                                          BigDecimal fullEmploymentStatusRange,
                                          @Nullable UserDto director,
                                          @Nullable UserDto ioDirector) {
}
