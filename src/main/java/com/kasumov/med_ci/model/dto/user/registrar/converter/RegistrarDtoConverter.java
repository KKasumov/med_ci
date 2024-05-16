package com.kasumov.med_ci.model.dto.user.registrar.converter;

import com.kasumov.med_ci.model.dto.user.registrar.CurrentRegistrarDto;
import com.kasumov.med_ci.model.entity.user.Employee;
import org.springframework.stereotype.Component;

@Component
public class RegistrarDtoConverter {
    public CurrentRegistrarDto entityToCurrentRegistrarDto(Employee entity) {
        return CurrentRegistrarDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .patronymic(entity.getPatronymic())
                .birthday(String.valueOf(entity.getBirthday()))
                .gender(entity.getGender())
                .build();
    }
}
