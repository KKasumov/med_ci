package com.kasumov.med_ci.model.dto.structure.license.converter;

import com.kasumov.med_ci.model.entity.structure.License;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.structure.license.LicenseDto;
import com.kasumov.med_ci.model.dto.structure.license.LicenseForCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LicenseDtoConverter {

    private final DateConvertor dateConvertor;

    public LicenseDto convertEntityToDto(License license) {
        return LicenseDto
                .builder()
                .id(license.getId())
                .name(license.getName())
                .number(license.getNumber())
                .startDate(dateConvertor.localDateToString(license.getDateFrom()))
                .endDate(dateConvertor.localDateToString(license.getDateTo()))
                .build();
    }

    public License convertToEntity(LicenseForCreateDto dto) {
        return License
                .builder()
                .name(dto.name())
                .number(dto.number())
                .dateFrom(dateConvertor.stringToLocalDate(dto.startDate()))
                .dateTo(dateConvertor.stringToLocalDate(dto.endDate()))
                .build();
    }
}
