package com.kasumov.med_ci.service.dto.structure;

import com.kasumov.med_ci.model.dto.structure.medicalOrganization.OrganizationWithDepartmentsDto;
import com.kasumov.med_ci.model.dto.structure.medicalOrganization.MedicalOrganizationDto;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.model.entity.user.Doctor;

public interface MedicalOrganizationDtoService {
    OrganizationWithDepartmentsDto getOrganizationWithDepartments();

    MedicalOrganizationDto setOrganizationDirector(MedicalOrganization medicalOrganization, Doctor newDirector);

    MedicalOrganizationDto removeDirector(MedicalOrganization medicalOrganization);

    MedicalOrganizationDto updateOrganization(MedicalOrganizationDto medicalOrganizationDto,
                                              MedicalOrganization medicalOrganization);

}
