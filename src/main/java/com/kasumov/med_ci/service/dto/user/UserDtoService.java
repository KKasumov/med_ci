package com.kasumov.med_ci.service.dto.user;

import com.kasumov.med_ci.model.dto.user.user.UserDto;
import com.kasumov.med_ci.model.entity.user.User;

public interface UserDtoService {
    User getUser (UserDto dto);
}
