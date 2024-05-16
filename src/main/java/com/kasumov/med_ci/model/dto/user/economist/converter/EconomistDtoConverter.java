package com.kasumov.med_ci.model.dto.user.economist.converter;

import com.kasumov.med_ci.model.dto.user.economist.CurrentEconomistDto;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EconomistDtoConverter {
    private final DateConvertor dateConvertor;

    public CurrentEconomistDto entityToCurrentEconomistDto(Employee entity) {
        return CurrentEconomistDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .birthday(dateConvertor.localDateToString(entity.getBirthday()))
                .gender(entity.getGender())
                .build();
    }
}
