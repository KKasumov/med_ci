package com.kasumov.med_ci.model.dto.medical.disease.converter;

import com.kasumov.med_ci.model.dto.medical.disease.DiseaseDto;
import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.structure.department.converter.DepartmentDtoConverter;
import com.kasumov.med_ci.model.entity.medical.Disease;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DiseaseDtoConverter {

    private final DepartmentDtoConverter departmentDtoConverter;

    public DiseaseDto diseaseToDiseaseDto(Disease disease) {

        return DiseaseDto.builder()
                .id(disease.getId())
                .identifier(disease.getIdentifier())
                .name(disease.getName())
                .isDisabled(disease.isDisabled())
                .ageType(disease.getAgeType())
                .department(getDepartmentDto(disease))
                .build();
    }

    private DepartmentDto getDepartmentDto(Disease disease) {
        return Optional.ofNullable(disease.getDepartment())
                .map(departmentDtoConverter::convertDepartmentToDepartmentDto)
                .orElse(null);
    }
}
