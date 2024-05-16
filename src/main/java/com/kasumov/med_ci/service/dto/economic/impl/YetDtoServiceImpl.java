package com.kasumov.med_ci.service.dto.economic.impl;

import com.kasumov.med_ci.service.entity.economic.YetService;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.economic.yet.YetDto;
import com.kasumov.med_ci.model.dto.economic.yet.converter.YetDtoConverter;
import com.kasumov.med_ci.model.entity.economic.Yet;
import com.kasumov.med_ci.service.dto.economic.YetDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YetDtoServiceImpl implements YetDtoService {

    private final YetDtoConverter yetDtoConverter;
    private final YetService yetService;
    private final DateConvertor dateConvertor;

    @Override
    public List<YetDto> getAllYet() {
        return yetService.getAll().stream()
                .map(yetDtoConverter::yetToYetDto)
                .toList();
    }

    @Override
    public YetDto editYetDto(YetDto yetDto) {
        Yet yet = yetService.findYetById(yetDto.id());
        yet.setPrice(yetDto.price());
        yet.setDayTo(dateConvertor.stringToLocalDate(yetDto.dayTo()));
        yet.setDayFrom(dateConvertor.stringToLocalDate(yetDto.dayFrom()));
        yetService.save(yet);
        return yetDtoConverter.yetToYetDto(yet);
    }
}