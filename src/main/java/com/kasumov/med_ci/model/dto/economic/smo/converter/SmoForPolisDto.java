package com.kasumov.med_ci.model.dto.economic.smo.converter;

import com.kasumov.med_ci.model.entity.economic.Smo;
import com.kasumov.med_ci.model.dto.economic.smo.SmoDto;
import org.springframework.stereotype.Component;

@Component
public class SmoForPolisDto {

    public SmoDto smoForPolisDtoToPolisDto(Smo smo) {
        return SmoDto.builder()
                .id(smo.getId())
                .name(smo.getName())
                .region(smo.getRegion())
                .build();
    }
}
