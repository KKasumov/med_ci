package com.kasumov.med_ci.service.entity.structure.impl;

import com.kasumov.med_ci.repository.structure.MedicalOrganizationRepository;
import com.kasumov.med_ci.model.entity.structure.MedicalOrganization;
import com.kasumov.med_ci.service.entity.structure.MedicalOrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalOrganizationServiceImpl implements MedicalOrganizationService {

    private final MedicalOrganizationRepository medicalOrganizationRepository;

    @Override
    public MedicalOrganization getOrganizationWithDepartments() {
        return medicalOrganizationRepository.getOrganizationWithDepartments();
    }

    @Override
    public MedicalOrganization findById(Long id) {
        return medicalOrganizationRepository.findMedicalOrganizationById(id);
    }

    @Override
    public boolean existsById(long id) {
        return medicalOrganizationRepository.existsById(id);
    }

}
