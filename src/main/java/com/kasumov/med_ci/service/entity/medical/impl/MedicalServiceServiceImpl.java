package com.kasumov.med_ci.service.entity.medical.impl;

import com.kasumov.med_ci.repository.medical.MedicalServiceRepository;
import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.model.enums.AgeType;
import com.kasumov.med_ci.service.entity.medical.MedicalServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalServiceServiceImpl implements MedicalServiceService {
    private final MedicalServiceRepository medicalServiceRepository;

    @Override
    public MedicalService save(MedicalService medicalService) {
        return medicalServiceRepository.save(medicalService);
    }

    @Override
    public boolean existByIdentifier(String identifier) {
        return medicalServiceRepository.existsByIdentifier(identifier);
    }

    @Override
    public List<MedicalService> getMedicalServicesByParameters(String pattern, @Nullable Long departmentId) {
        return medicalServiceRepository.getMedicalServiceByDepartmentOrPattern(departmentId, pattern, AgeType.NO);
    }

    @Override
    public MedicalService findByIdWithDepartment(Long id) {
        return medicalServiceRepository.findByIdWithDepartment(id);
    }

    @Override
    public void deleteById(long medicalServiceId) {
        medicalServiceRepository.deleteById(medicalServiceId);
    }

    @Override
    public boolean existsById(long medicalServiceId) {
        return medicalServiceRepository.existsById(medicalServiceId);
    }
}