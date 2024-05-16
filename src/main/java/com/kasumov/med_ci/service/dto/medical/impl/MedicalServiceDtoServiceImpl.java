package com.kasumov.med_ci.service.dto.medical.impl;

import com.kasumov.med_ci.service.entity.medical.MedicalServiceService;
import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDto;
import com.kasumov.med_ci.model.dto.medical.medicalService.NewMedicalServiceDto;
import com.kasumov.med_ci.model.dto.medical.medicalService.converter.MedicalServiceDtoConverter;
import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.service.dto.medical.MedicalServiceDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalServiceDtoServiceImpl implements MedicalServiceDtoService {
    private final MedicalServiceService medicalServiceService;
    private final MedicalServiceDtoConverter medicalServiceDtoConverter;

    @Override
    public MedicalServiceDto saveNewServiceDto(NewMedicalServiceDto medicalServiceDto) {
        return medicalServiceDtoConverter.convertMedServiceToMedSerDto(medicalServiceService
                .save(MedicalService.builder()
                        .identifier(medicalServiceDto.identifier())
                        .name(medicalServiceDto.name())
                        .isDisabled(medicalServiceDto.isDisabled())
                        .build()));
    }

    @Override
    public List<MedicalServiceDto> getMedicalServicesByParameters(String pattern, @Nullable Long departmentId) {
        return medicalServiceService.getMedicalServicesByParameters(pattern,departmentId)
                .stream()
                .map(medicalServiceDtoConverter::convertMedServiceToMedSerDto)
                .sorted(Comparator.comparing(MedicalServiceDto::id))
                .toList();
    }



}
