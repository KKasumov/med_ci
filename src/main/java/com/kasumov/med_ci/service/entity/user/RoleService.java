package com.kasumov.med_ci.service.entity.user;

import com.kasumov.med_ci.model.entity.user.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findById(Long id);

    Role getRole(String role);
}
