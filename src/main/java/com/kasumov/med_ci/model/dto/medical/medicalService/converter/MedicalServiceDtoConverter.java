package com.kasumov.med_ci.model.dto.medical.medicalService.converter;

import com.kasumov.med_ci.model.dto.structure.department.DepartmentDto;
import com.kasumov.med_ci.model.dto.structure.department.converter.DepartmentDtoConverter;
import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.model.entity.structure.Department;
import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDto;
import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDtoShort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MedicalServiceDtoConverter {
    private final DepartmentDtoConverter departmentDtoConverter;

    public MedicalServiceDto convertMedServiceToMedSerDto(MedicalService medicalService) {

        return MedicalServiceDto.builder()
                .id(medicalService.getId())
                .identifier(medicalService.getIdentifier())
                .name(medicalService.getName())
                .isDisabled(medicalService.isDisabled())
                .departmentDto(getDepartment(medicalService))
                .build();
    }

    private DepartmentDto getDepartment(MedicalService medicalService) {
        Department department = medicalService.getDepartment();
        if (department != null) {
            return departmentDtoConverter.convertDepartmentToDepartmentDto(department);
        }
        return null;
    }

    public MedicalServiceDtoShort convertMedServiceToMedServiceDtoWithThreeFields(MedicalService medicalService) {

        return MedicalServiceDtoShort.builder()
                .id(medicalService.getId())
                .identifier(medicalService.getIdentifier())
                .name(medicalService.getName())
                .build();
    }
}
