package com.kasumov.med_ci.model.dto.structure.medicalOrganization.converter;

import com.kasumov.med_ci.model.dto.user.user.converter.UserDtoConverter;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.structure.department.converter.DepartmentDtoConverter;
import com.kasumov.med_ci.model.dto.structure.medicalOrganization.OrganizationWithDepartmentsDto;
import lombok.RequiredArgsConstructor;
import com.kasumov.med_ci.model.dto.structure.medicalOrganization.MedicalOrganizationDto;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedicalOrganizationDtoConverter {

    private final DateConvertor dateConvertor;
    private final UserDtoConverter userDtoConverter;
    private final DepartmentDtoConverter departmentWithChiefDto;

    public OrganizationWithDepartmentsDto convertMedOrgToMedOrgDto(MedicalOrganization organization) {
        return OrganizationWithDepartmentsDto.builder()
                .id(organization.getId())
                .code(organization.getCode())
                .name(organization.getName())
                .legalAddress(organization.getLegalAddress())
                .ogrn(organization.getOgrn())
                .startDate(dateConvertor.localDateToString(organization.getStartDate()))
                .endDate(dateConvertor.localDateToString(organization.getEndDate()))
                .fullEmploymentStatusRange(organization.getFullEmploymentStatusRange())
                .director(userDtoConverter.convertUserToUserDto(organization.getDirector()))
                .ioDirector(userDtoConverter.convertUserToUserDto(organization.getIoDirector()))
                .departments(departmentWithChiefDto.convertToDepartmentWithChiefDtoList(organization.getDepartments()))
                .build();
    }

    public MedicalOrganizationDto convertMedicalOrganizationToMedicalOrganizationDto(MedicalOrganization medicalOrganization) {

        return MedicalOrganizationDto.builder()
                .id(medicalOrganization.getId())
                .code(medicalOrganization.getCode())
                .name(medicalOrganization.getName())
                .legalAddress(medicalOrganization.getLegalAddress())
                .ogrn(medicalOrganization.getOgrn())
                .startDate(dateConvertor.localDateToString(medicalOrganization.getStartDate()))
                .endDate(dateConvertor.localDateToString(medicalOrganization.getEndDate()))
                .fullEmploymentStatusRange(medicalOrganization.getFullEmploymentStatusRange())
                .director(userDtoConverter.convertUserToUserDto(medicalOrganization.getDirector()))
                .ioDirector(userDtoConverter.convertUserToUserDto(medicalOrganization.getIoDirector()))
                .build();
    }
}
