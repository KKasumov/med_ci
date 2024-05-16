package com.kasumov.med_ci.service.dto.economic.impl;

import com.kasumov.med_ci.service.entity.economic.SmoService;
import com.kasumov.med_ci.model.dto.economic.smo.SmoDto;
import com.kasumov.med_ci.model.dto.economic.smo.converter.SmoDtoConverter;
import com.kasumov.med_ci.model.entity.economic.Smo;
import com.kasumov.med_ci.model.enums.Region;
import com.kasumov.med_ci.service.dto.economic.SmoDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SmoDtoServiceImpl implements SmoDtoService {

    private final SmoDtoConverter smoDtoConverter;
    private final SmoService smoService;

    @Override
    public List<SmoDto> getAllSmo() {
        return smoService.getAll().stream()
                .map(smoDtoConverter::smoToSmoDto)
                .toList();
    }

    @Override
    public List<SmoDto> getAllSmoDtoByParameters(Region region, String pattern) {
        List<Smo> smoList = smoService.getSmoListByParameters(region, pattern);
        return smoList.stream()
                .map(smoDtoConverter::smoToSmoDto)
                .toList();
    }
}