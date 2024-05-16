package com.kasumov.med_ci.service.dto.medical;

import com.kasumov.med_ci.model.dto.medical.disease.DiseaseDto;

import java.util.List;

public interface DiseaseDtoService {
    DiseaseDto blockedDiseases(Long diseaseId);
    DiseaseDto unBlockedDiseases(Long diseaseId);
    List<DiseaseDto> getAllDiseaseDtoByParameters(String pattern,Long departmentId);
}
