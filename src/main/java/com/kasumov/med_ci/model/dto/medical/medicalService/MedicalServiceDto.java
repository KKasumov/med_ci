package com.kasumov.med_ci.model.dto.medical.medicalService;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record MedicalServiceDto(
        long id,
        String identifier,
        String name,
        boolean isDisabled,
        @Nullable DepartmentDto departmentDto
        ) {
}
