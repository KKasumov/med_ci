package com.kasumov.med_ci.model.dto.user.user;

import com.kasumov.med_ci.model.enums.Gender;
import lombok.Builder;
import org.springframework.lang.Nullable;

@Builder
public record UserDto(long id,
                      String email,
                      String firstName,
                      String lastName,
                      @Nullable String patronymic,
                      String birthday,              //format dd.MM.yyyy
                      Gender gender) {
}

