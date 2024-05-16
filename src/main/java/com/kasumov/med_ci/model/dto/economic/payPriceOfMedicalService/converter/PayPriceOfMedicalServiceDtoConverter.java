package com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.converter;

import com.kasumov.med_ci.model.entity.economic.price.PayPriceOfMedicalService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.PayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.medical.medicalService.converter.MedicalServiceDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class PayPriceOfMedicalServiceDtoConverter {

    private final DateConvertor dateConvertor;
    private final MedicalServiceDtoConverter medicalServiceDtoConverter;

    public PayPriceOfMedicalServiceDto convertPayPriceOfMedServiceToPayPriceOfMedSerDto(
            PayPriceOfMedicalService payPriceOfMedicalService) {
        return PayPriceOfMedicalServiceDto.builder()
                .id(payPriceOfMedicalService.getId())
                .dayFrom(dateConvertor.localDateToString(payPriceOfMedicalService.getDayFrom()))
                .dayTo(dateConvertor.localDateToString(payPriceOfMedicalService.getDayTo()))
                .moneyInPrice(payPriceOfMedicalService.getMoneyInPrice())
                .medicalService(medicalServiceDtoConverter.convertMedServiceToMedSerDto(
                        payPriceOfMedicalService.getMedicalService()))
                .build();
    }

    public List<PayPriceOfMedicalServiceDto> convertPayPriceOfMedServiceToPayPriceOfMedSerDtoList(
            Set<PayPriceOfMedicalService> payPriceOfMedicalService) {
        return payPriceOfMedicalService.stream()
                .sorted((x, y) ->x.getId().compareTo(y.getId()))
                .map(this::convertPayPriceOfMedServiceToPayPriceOfMedSerDto)
                .toList();
    }
}
