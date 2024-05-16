package com.kasumov.med_ci.model.dto.economic.omsPriceOfMedicalService.converter;

import com.kasumov.med_ci.model.dto.economic.omsPriceOfMedicalService.OmsPriceOfMedicalServiceDto;
import com.kasumov.med_ci.model.entity.economic.price.OmsPriceOfMedicalService;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class OmsPriceOfMedicalServiceDtoConverter {
    private final DateConvertor dateConvertor;

    public OmsPriceOfMedicalServiceDto convertOmsPriceOfMedicalServiceToOmsPriceOfMedicalServiceDto(
            OmsPriceOfMedicalService omsPriceOfMedicalService) {
        return OmsPriceOfMedicalServiceDto.builder()
                .id(omsPriceOfMedicalService.getId())
                .dayFrom(dateConvertor.localDateToString(omsPriceOfMedicalService.getDayFrom()))
                .dayTo(dateConvertor.localDateToString(omsPriceOfMedicalService.getDayTo()))
                .yet(omsPriceOfMedicalService.getYet())
                .build();
    }

    public List<OmsPriceOfMedicalServiceDto> convertOmsPriceOfMedicalServiceToOmsPriceOfMedicalServiceDtoList(
            Set<OmsPriceOfMedicalService> omsPriceOfMedicalService) {
        return omsPriceOfMedicalService.stream()
                .sorted((x, y) ->x.getId().compareTo(y.getId()))
                .map(this::convertOmsPriceOfMedicalServiceToOmsPriceOfMedicalServiceDto)
                .toList();
    }
}

