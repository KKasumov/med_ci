package com.kasumov.med_ci.service.dto.medical;

import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServiceDto;
import com.kasumov.med_ci.model.dto.medical.medicalService.NewMedicalServiceDto;
import org.springframework.lang.Nullable;

import java.util.List;

public interface MedicalServiceDtoService {
    MedicalServiceDto saveNewServiceDto(NewMedicalServiceDto medicalServiceDto);
    List<MedicalServiceDto> getMedicalServicesByParameters(String pattern, @Nullable Long departmentId);
}
