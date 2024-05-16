package com.kasumov.med_ci.model.dto.user.user.converter;

import com.kasumov.med_ci.model.entity.user.User;
import com.kasumov.med_ci.util.DateConvertor;
import com.kasumov.med_ci.model.dto.user.user.UserDto;
import com.kasumov.med_ci.model.dto.user.user.UserRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {
    private final DateConvertor dateConvertor;

    public UserDto convertUserToUserDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .patronymic(user.getPatronymic())
                .birthday(dateConvertor.localDateToString(user.getBirthday()))
                .gender(user.getGender())
                .build();
    }

    public UserRoleDto convertUserToUserRoleDto(User user) {
        if (user == null) {
            return null;
        }

        return UserRoleDto.builder()
                .role(user.getRole())
                .build();
    }
    public User userDtoToEntity(UserDto dto) {
        return User.baseBuilder()
                .id(dto.id())
                .email(dto.email())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .patronymic(dto.patronymic())
                .birthday(dateConvertor.stringToLocalDate(dto.birthday()))
                .gender(dto.gender())
                .build();
    }
}
