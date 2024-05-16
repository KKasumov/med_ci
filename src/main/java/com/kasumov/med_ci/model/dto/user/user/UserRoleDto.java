package com.kasumov.med_ci.model.dto.user.user;

import com.kasumov.med_ci.model.entity.user.Role;
import lombok.Builder;

@Builder
public record UserRoleDto(Role role) {
}
