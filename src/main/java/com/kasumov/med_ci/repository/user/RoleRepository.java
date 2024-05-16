package com.kasumov.med_ci.repository.user;

import com.kasumov.med_ci.model.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);
}