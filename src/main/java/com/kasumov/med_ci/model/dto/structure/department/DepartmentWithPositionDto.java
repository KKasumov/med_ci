package com.kasumov.med_ci.model.dto.structure.department;

import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.model.dto.structure.position.PositionDto;
import lombok.Builder;

import java.util.List;

@Builder
public record DepartmentWithPositionDto(long id,
                                        String name,
                                        AgeType ageType,
                                        List<PositionDto>positions) {
}
