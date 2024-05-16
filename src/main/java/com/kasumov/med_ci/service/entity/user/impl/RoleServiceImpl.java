package com.kasumov.med_ci.service.entity.user.impl;

import com.kasumov.med_ci.repository.user.RoleRepository;
import com.kasumov.med_ci.service.entity.user.RoleService;
import com.kasumov.med_ci.model.entity.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role getRole(String role) {
        return roleRepository.findRoleByName(role);
    }
}
