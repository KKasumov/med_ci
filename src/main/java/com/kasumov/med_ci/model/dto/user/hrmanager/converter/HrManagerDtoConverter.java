package com.kasumov.med_ci.model.dto.user.hrmanager.converter;

import com.kasumov.med_ci.model.dto.user.hrmanager.CurrentHrManagerDto;
import com.kasumov.med_ci.model.entity.user.Employee;
import com.kasumov.med_ci.util.DateConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HrManagerDtoConverter {
    private final DateConvertor dateConvertor;

    public CurrentHrManagerDto entityToCurrentHrManagerDto(Employee entity) {
        return CurrentHrManagerDto.builder()
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
