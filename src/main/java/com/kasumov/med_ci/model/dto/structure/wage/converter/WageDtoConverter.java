package com.kasumov.med_ci.model.dto.structure.wage.converter;

import com.kasumov.med_ci.model.entity.structure.Position;
import com.kasumov.med_ci.model.entity.structure.Wage;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.structure.wage.NewWageDto;
import com.kasumov.med_ci.model.dto.structure.wage.WageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WageDtoConverter {

    private final DateConvertor dateConvertor;

    public WageDto convertToListWageToWageDto(Position position) {
        List<Wage> wages = position.getWages().stream().toList();
        for (int i = 0; i < wages.size(); i++) {
            if (dateConvertor.stringToLocalDate(wages.stream()
                    .map(this::convertEndDate)
                    .toList().get(i).dateEnd()).isAfter(LocalDate.now())) {
                return wages.stream()
                        .map(this::convertEndDate)
                        .toList().get(i);
            }
        }
        return null;
    }

    private WageDto convertEndDate(Wage wage) {
        return WageDto.builder()
                .id(wage.getId())
                .dateStart(dateConvertor.localDateToString(wage.getDateStart()))
                .dateEnd(dateConvertor.localDateToString(wage.getDateEnd()))
                .value(wage.getValue())
                .build();
    }

    public WageDto wageToWageDtoConvert(Wage wage) {
        LocalDate dateEnd = null;
        if (wage.getDateEnd() != null) {
            dateEnd = dateConvertor.stringToLocalDate(String.valueOf(wage.getDateEnd()));
        }
        return WageDto.builder()
                .id(wage.getId())
                .dateStart(wage.getDateStart().toString())
                .dateEnd(String.valueOf(dateEnd))
                .value(wage.getValue())
                .build();
    }


    public Wage newWageDtoConvertToEntity(NewWageDto newWageDto) {
        LocalDate dateEnd = null;
        if (newWageDto.dateEnd() != null) {
            dateEnd = dateConvertor.stringToLocalDate(newWageDto.dateEnd());
        }
        return Wage.builder()
                .dateStart(dateConvertor.stringToLocalDate(newWageDto.dateStart()))
                .dateEnd(dateEnd)
                .value(newWageDto.value())
                .build();
    }

    public WageDto convertWageToWageDto(Wage wage) {
        return WageDto.builder()
                .id(wage.getId())
                .dateStart(wage.getDateStart().toString())
                .dateEnd(wage.getDateEnd() != null ? wage.getDateEnd().toString(): null)
                .value(wage.getValue())
                .build();
    }
}
