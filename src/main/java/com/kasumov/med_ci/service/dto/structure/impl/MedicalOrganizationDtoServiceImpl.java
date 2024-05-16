package com.kasumov.med_ci.service.dto.structure.impl;

import com.kasumov.med_ci.service.entity.structure.MedicalOrganizationService;
import com.kasumov.med_ci.service.entity.user.RoleService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.structure.medicalOrganization.MedicalOrganizationDto;
import com.kasumov.med_ci.model.dto.structure.medicalOrganization.OrganizationWithDepartmentsDto;
import com.kasumov.med_ci.model.dto.structure.medicalOrganization.converter.MedicalOrganizationDtoConverter;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.entity.user.Doctor;
import com.kasumov.med_ci.model.enums.RolesEnum;
import com.kasumov.med_ci.service.dto.structure.MedicalOrganizationDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class MedicalOrganizationDtoServiceImpl implements MedicalOrganizationDtoService {

    private final DateConvertor dateConvertor;
    private final MedicalOrganizationService medicalOrganizationService;
    private final MedicalOrganizationDtoConverter medicalOrganizationDtoConverter;
    private final RoleService roleService;


    @Override
    public OrganizationWithDepartmentsDto getOrganizationWithDepartments() {
        return medicalOrganizationDtoConverter
                .convertMedOrgToMedOrgDto(medicalOrganizationService.getOrganizationWithDepartments());
    }

    @Override
    @Transactional
    public MedicalOrganizationDto setOrganizationDirector(MedicalOrganization medicalOrganization, Doctor newDirector) {

        //если был старый директор - записать ему новую роль -роль доктор
        Doctor oldDirector = medicalOrganization.getDirector();
        if (oldDirector != null) {
            oldDirector.setRole(roleService.getRole(RolesEnum.DOCTOR.name()));
        }

        // у пришедшего в метод доктора меняем роль на директор
        newDirector.setRole(roleService.getRole(RolesEnum.DIRECTOR.name()));
        medicalOrganization.setDirector(newDirector);

        //если главным врачом назначаем ио главного врача, то сделать ио главного null
        Doctor currentIoDirector = medicalOrganization.getIoDirector();
        if (currentIoDirector != null && currentIoDirector.getId().equals(newDirector.getId())) {
            medicalOrganization.setIoDirector(null);
        }
        return medicalOrganizationDtoConverter.convertMedicalOrganizationToMedicalOrganizationDto(medicalOrganization);
    }

    @Override
    @Transactional
    public MedicalOrganizationDto removeDirector(MedicalOrganization medicalOrganization) {
        medicalOrganization.getDirector().setRole(roleService.getRole(RolesEnum.DOCTOR.name()));
        medicalOrganization.setDirector(null);
        return medicalOrganizationDtoConverter.convertMedicalOrganizationToMedicalOrganizationDto(medicalOrganization);
    }

    @Override
    @Transactional
    public MedicalOrganizationDto updateOrganization(MedicalOrganizationDto medicalOrganizationDto,
                                                     MedicalOrganization medicalOrganization) {
        medicalOrganization.setCode(medicalOrganizationDto.code());
        medicalOrganization.setName(medicalOrganizationDto.name());
        medicalOrganization.setLegalAddress(medicalOrganizationDto.legalAddress());
        medicalOrganization.setOgrn(medicalOrganizationDto.ogrn());
        medicalOrganization.setStartDate(dateConvertor.stringToLocalDate(medicalOrganizationDto.startDate()));
        medicalOrganization.setEndDate(dateConvertor.stringToLocalDate(medicalOrganizationDto.endDate()));
        medicalOrganization.setFullEmploymentStatusRange(medicalOrganizationDto.fullEmploymentStatusRange());
        return medicalOrganizationDtoConverter.convertMedicalOrganizationToMedicalOrganizationDto(medicalOrganization);
    }

}
