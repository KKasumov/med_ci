package com.kasumov.med_ci.service.dto.medical.impl;

import com.kasumov.med_ci.service.entity.medical.DiseaseService;
import com.kasumov.med_ci.model.dto.medical.disease.DiseaseDto;
import com.kasumov.med_ci.model.dto.medical.disease.converter.DiseaseDtoConverter;
import com.kasumov.med_ci.model.entity.medical.Disease;
import com.kasumov.med_ci.service.dto.medical.DiseaseDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class DiseaseDtoServiceImpl implements DiseaseDtoService {
    private final DiseaseService diseaseService;
    private final DiseaseDtoConverter diseaseDtoConverter;


    @Override
    public DiseaseDto blockedDiseases(Long diseaseId) {
        Disease disease = diseaseService.findByIdWithDepartment(diseaseId);
        disease.setDisabled(true);
        diseaseService.save(disease);
        return diseaseDtoConverter.diseaseToDiseaseDto(disease);
    }

    @Override
    public DiseaseDto unBlockedDiseases(Long diseaseId) {
        Disease disease = diseaseService.findByIdWithDepartment(diseaseId);
        disease.setDisabled(false);
        diseaseService.save(disease);
        return diseaseDtoConverter.diseaseToDiseaseDto(disease);
    }

    @Override
    public List<DiseaseDto> getAllDiseaseDtoByParameters(String pattern, Long departmentId) {
        return diseaseService.getDiseaseListByParameters(pattern, departmentId)
                .stream()
                .sorted((o1, o2) -> (int) (o1.getId() - o2.getId()))
                .map(diseaseDtoConverter::diseaseToDiseaseDto)
                .sorted(Comparator.comparing(DiseaseDto::id))
                .toList();
    }

}
