package com.kasumov.med_ci.service.dto.user.impl;

import com.kasumov.med_ci.model.dto.user.user.UserDto;
import com.kasumov.med_ci.model.dto.user.user.converter.UserDtoConverter;
import com.kasumov.med_ci.repository.user.RoleRepository;
import com.kasumov.med_ci.service.entity.user.UserService;

import com.kasumov.med_ci.model.entity.user.User;
import com.kasumov.med_ci.service.dto.user.UserDtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDtoServiceImpl implements UserDtoService {
    private final UserDtoConverter userDtoConverter;
    private final UserService userService;
    private final RoleRepository roleRepository;

    @Override
    public User getUser(UserDto dto) {
        return userDtoConverter.userDtoToEntity(dto);
    }
}
