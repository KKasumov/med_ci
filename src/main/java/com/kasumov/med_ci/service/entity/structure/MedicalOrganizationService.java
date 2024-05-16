package com.kasumov.med_ci.service.entity.structure;

import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;

public interface MedicalOrganizationService {

    MedicalOrganization getOrganizationWithDepartments();

    MedicalOrganization findById(Long id);

    boolean existsById(long id);

}
