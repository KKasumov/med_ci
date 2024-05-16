package com.kasumov.med_ci.model.dto.user.role;

import lombok.Builder;

@Builder
public record RoleDto(long id,
                      String name) {
}
