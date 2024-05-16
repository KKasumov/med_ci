package com.kasumov.med_ci.service.dto.economic;

import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServicePriceDto.MedicalServicePriceDto;
import com.kasumov.med_ci.model.entity.medical.MedicalService;

public interface MedicalServicePricesDtoService {
    MedicalServicePriceDto getPricesMedicalService(MedicalService medicalService);
}
