package com.kasumov.med_ci.service.entity.medical.impl;

import com.kasumov.med_ci.repository.medical.DiseaseRepository;
import com.kasumov.med_ci.model.entity.medical.Disease;
import com.kasumov.med_ci.service.entity.medical.DiseaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiseaseServiceImpl implements DiseaseService {

    private final DiseaseRepository diseaseRepository;

    @Override
    public boolean existsById(long diseaseId) {
        return diseaseRepository.existsById(diseaseId);
    }

    @Override
    public void deleteById(long diseaseId) {
        diseaseRepository.deleteById(diseaseId);
    }

    @Override
    public Disease findByIdWithDepartment(long diseaseId) {
        return diseaseRepository.findByIdWithDepartment(diseaseId);
    }

    @Override
    public Disease save(Disease disease) {
        return diseaseRepository.save(disease);
    }

    @Override
    public List<Disease> getDiseaseListByParameters(String pattern, Long departmentId) {
        pattern = pattern == null ? "" : pattern;
        return diseaseRepository.getDiseaseByParameters(pattern, departmentId);
    }
}
