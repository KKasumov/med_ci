package com.kasumov.med_ci.service.dto.structure.impl;

import com.kasumov.med_ci.service.entity.structure.WageService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.structure.wage.WageDto;
import com.kasumov.med_ci.model.dto.structure.wage.converter.WageDtoConverter;
import com.kasumov.med_ci.model.entity.structure.Wage;
import com.kasumov.med_ci.service.dto.structure.WageDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WageDtoServiceImpl implements WageDtoService {
    private final WageDtoConverter wageDtoConverter;
    private final WageService wageService;
    private final DateConvertor dateConvertor;

    @Override
    public WageDto updateWageOfPosition(Wage wage, WageDto wageDto) {
        if (wageDto.value() != null) {
            wage.setValue(wageDto.value());
        }
        if (wageDto.dateStart() != null) {
            wage.setDateStart(dateConvertor.stringToLocalDate(wageDto.dateStart()));
        }
        if (wageDto.dateEnd() == null) {
            wage.setDateEnd(null);
        } else {
            wage.setDateEnd(dateConvertor.stringToLocalDate(wageDto.dateEnd()));
        }
        wageService.updateWageValue(wage);
        return wageDtoConverter.convertWageToWageDto(wage);
    }

    @Override
    public LocalDate getLocalDateByString(String date) {
        return dateConvertor.stringToLocalDate(date);
    }
}
