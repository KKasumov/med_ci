package com.kasumov.med_ci.service.dto.economic;

import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.NewPayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.PayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.entity.medical.MedicalService;

public interface PayPriceOfMedicalServiceDtoService {
    PayPriceOfMedicalServiceDto saveNewPayPriceOfMedicalServiceDto(
            NewPayPriceOfMedicalServiceDto payPriceOfMedicalServiceDto, MedicalService medicalService);
}