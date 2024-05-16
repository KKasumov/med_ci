package com.kasumov.med_ci.model.dto.structure.department;


import com.kasumov.med_ci.model.enums.AgeType;
import lombok.Builder;

@Builder
public record DepartmentDto(long id,
                            String name,
                            AgeType ageType) {
}
