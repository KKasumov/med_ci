package com.kasumov.med_ci.service.dto.economic.impl;

import com.kasumov.med_ci.service.entity.economic.PayPriceOfMedicalServiceService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.NewPayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.PayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.converter.PayPriceOfMedicalServiceDtoConverter;
import com.kasumov.med_ci.model.entity.economic.price.PayPriceOfMedicalService;
import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.service.dto.economic.PayPriceOfMedicalServiceDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayPriceOfMedicalServiceDtoServiceImpl implements PayPriceOfMedicalServiceDtoService {
    private final PayPriceOfMedicalServiceService payPriceOfMedicalServiceService;
    private final PayPriceOfMedicalServiceDtoConverter payPriceOfMedicalServiceDtoConverter;
    private final DateConvertor dateConvertor;

    @Override
    public PayPriceOfMedicalServiceDto saveNewPayPriceOfMedicalServiceDto(
            NewPayPriceOfMedicalServiceDto payPriceOfMedicalServiceDto, MedicalService medicalService) {
        return payPriceOfMedicalServiceDtoConverter.convertPayPriceOfMedServiceToPayPriceOfMedSerDto(
                payPriceOfMedicalServiceService.save(PayPriceOfMedicalService.builder()
                        .dayFrom(dateConvertor.stringToLocalDate(payPriceOfMedicalServiceDto.dayFrom()))
                        .dayTo(dateConvertor.stringToLocalDate(payPriceOfMedicalServiceDto.dayTo()))
                        .moneyInPrice(payPriceOfMedicalServiceDto.moneyInPrice())
                        .medicalService(medicalService)
                        .build()));
    }
}
