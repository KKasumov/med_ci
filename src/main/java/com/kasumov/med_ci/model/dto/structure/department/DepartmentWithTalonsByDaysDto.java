package com.kasumov.med_ci.model.dto.structure.department;

import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.model.dto.medical.talon.TalonsByDaysDto;
import lombok.Builder;

import java.util.List;

@Builder
public record DepartmentWithTalonsByDaysDto(long id,
                                            String name,
                                            AgeType ageType,
                                            List<TalonsByDaysDto> days) {
}
