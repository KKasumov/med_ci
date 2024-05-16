package com.kasumov.med_ci.model.dto.structure.department;

import com.kasumov.med_ci.model.dto.user.user.UserDto;
import com.kasumov.med_ci.model.enums.AgeType;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record DepartmentWithChiefDto(long id,
                                     String name,
                                     AgeType ageType,
                                     @Nullable UserDto chiefDoctor,
                                     @Nullable UserDto ioChiefDoctor) {
}
