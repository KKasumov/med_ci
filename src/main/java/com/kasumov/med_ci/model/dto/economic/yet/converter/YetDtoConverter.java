package com.kasumov.med_ci.model.dto.economic.yet.converter;


import com.kasumov.med_ci.model.entity.economic.Yet;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.economic.yet.YetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class YetDtoConverter {

    private final DateConvertor dateConvertor;


    public YetDto yetToYetDto(Yet yet) {
        return YetDto.builder()
                .id(yet.getId())
                .price(yet.getPrice())
                .dayFrom(dateConvertor.localDateToString(yet.getDayFrom()))
                .dayTo(yet.getDayTo() == null ? null : dateConvertor.localDateToString(yet.getDayTo()))
                .build();
    }
}

