package com.kasumov.med_ci.service.entity.medical.impl;

import com.kasumov.med_ci.repository.medical.VisitRepository;
import com.kasumov.med_ci.model.dto.medical.visit.VisitMedicalServiceNativeDto;
import com.kasumov.med_ci.service.entity.medical.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    @Override
    public boolean existsByMedicalServicesId(long medicalServiceId) {
        return visitRepository.existsByMedicalServicesId(medicalServiceId);
    }

    @Override
    public VisitMedicalServiceNativeDto findWithMedicalServicesForOms(long visitId) {
        return visitRepository.findWithMedicalServicesForOms(visitId);
    }

    @Override
    public VisitMedicalServiceNativeDto findWithMedicalServicesForDms(long visitId) {
        return visitRepository.findWithMedicalServicesForDms(visitId);
    }


}