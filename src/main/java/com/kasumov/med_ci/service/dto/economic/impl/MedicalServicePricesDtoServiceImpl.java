package com.kasumov.med_ci.service.dto.economic.impl;

import com.kasumov.med_ci.model.dto.economic.omsPriceOfMedicalService.OmsPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.economic.omsPriceOfMedicalService.converter.OmsPriceOfMedicalServiceDtoConverter;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.PayPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.dto.economic.payPriceOfMedicalService.converter.PayPriceOfMedicalServiceDtoConverter;
import com.kasumov.med_ci.model.dto.medical.medicalService.MedicalServicePriceDto.MedicalServicePriceDto;


import com.kasumov.med_ci.model.entity.medical.MedicalService;
import com.kasumov.med_ci.service.dto.economic.MedicalServicePricesDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class MedicalServicePricesDtoServiceImpl implements MedicalServicePricesDtoService {
    private final OmsPriceOfMedicalServiceDtoConverter omsPriceOfMedicalServiceDtoConverter;
    private final PayPriceOfMedicalServiceDtoConverter payPriceOfMedicalServiceDtoConverter;

    @Override
    public MedicalServicePriceDto getPricesMedicalService(MedicalService medicalService) {
        return MedicalServicePriceDto.builder()
                .id(medicalService.getId())
                .listPayPriceOfMedicalServiceDto(getPayPriceList(medicalService))
                .listOmsPriceOfMedicalServiceDto(getOmsPriceList(medicalService))
                .build();
    }

    private List<PayPriceOfMedicalServiceDto> getPayPriceList(MedicalService medicalService) {
        return payPriceOfMedicalServiceDtoConverter
                .convertPayPriceOfMedServiceToPayPriceOfMedSerDtoList(
                        medicalService.getPayPriceOfMedicalServices());
    }

    private List<OmsPriceOfMedicalServiceDto> getOmsPriceList(MedicalService medicalService) {
        return omsPriceOfMedicalServiceDtoConverter
                .convertOmsPriceOfMedicalServiceToOmsPriceOfMedicalServiceDtoList(
                        medicalService.getOmsPriceOfMedicalServices());
    }
}
