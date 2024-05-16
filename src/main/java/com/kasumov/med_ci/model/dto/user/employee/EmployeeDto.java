package com.kasumov.med_ci.model.dto.user.employee;

import lombok.Builder;

@Builder
public record EmployeeDto(Long id,
                          String email,
                          String snils,
                          boolean enabled) {
}
