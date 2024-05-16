package com.kasumov.med_ci.service.entity.medical.impl;

import com.kasumov.med_ci.repository.medical.AppealRepository;
import com.kasumov.med_ci.model.entity.medical.Appeal;
import com.kasumov.med_ci.service.entity.medical.AppealService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppealServiceImpl implements AppealService {

    private final AppealRepository appealRepository;

    @Override
    public boolean existsByDiseaseId(long diseaseId) {
        return appealRepository.existsByDiseaseId(diseaseId);
    }

    @Override
    public boolean existsById(Long appealId) {
        return appealRepository.existsById(appealId);
    }

    @Override
    public Appeal findWithPatient(long appealId) {
        return appealRepository.findWithPatient(appealId);
    }

    @Override
    public Appeal findWithVisits(long appealId) {
        return appealRepository.findWithVisits(appealId);
    }

}
