package com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServicePriceDto;

import com.kasumov.med_ci.model.dto.economic.omsPriceOfMedicalService.OmsPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.PayPriceOfMedicalServiceDto;
import lombok.Builder;

import java.util.List;

@Builder
public record MedicalServicePriceDto(
        Long id,
        List<OmsPriceOfMedicalServiceDto> listOmsPriceOfMedicalServiceDto,
        List<PayPriceOfMedicalServiceDto>listPayPriceOfMedicalServiceDto
) {
}
