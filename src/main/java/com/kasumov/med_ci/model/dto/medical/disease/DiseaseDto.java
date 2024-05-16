package com.kasumov.med_ci.model.dto.medical.disease;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.enums.AgeType;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record DiseaseDto(
        long id,
        String identifier,
        String name,
        boolean isDisabled,
        AgeType ageType,
        @Nullable
        DepartmentDto department
) {
}
