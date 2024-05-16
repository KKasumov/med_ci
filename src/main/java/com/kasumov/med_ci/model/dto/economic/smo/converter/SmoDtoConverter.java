package com.kasumov.med_ci.model.dto.economic.smo.converter;

import com.kasumov.med_ci.model.entity.economic.Smo;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.economic.smo.SmoDto;
import com.kasumov.med_ci.model.dto.economic.smo.SmoForPolisDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmoDtoConverter {

    private final DateConvertor dateConvertor;

    public SmoForPolisDto toSmoForPolisDto(Smo smo) {
        return SmoForPolisDto.builder()
                .id(smo.getId())
                .name(smo.getName())
                .region(smo.getRegion())
                .code(smo.getCode())
                .build();
    }

    public SmoDto smoToSmoDto(Smo smo) {
        return SmoDto.builder()
                .id(smo.getId())
                .name(smo.getName())
                .region(smo.getRegion())
                .startDate(dateConvertor.localDateToString(smo.getStartDate()))
                .endDate(dateConvertor.localDateToString(smo.getEndDate()))
                .code(smo.getCode())
                .build();
    }

    public SmoForPolisDto convertSmoToSmoForPolisDto(Smo smo) {
        return SmoForPolisDto.builder()
                .id(smo.getId())
                .name(smo.getName())
                .region(smo.getRegion())
                .code(smo.getCode())
                .build();
    }
}
